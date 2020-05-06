package com.developervishalsehgal.constraintlayoutchallenge.cs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.admin.add_produk;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_rekapan extends AppCompatActivity {
    EditText add_harga, add_barang_beli,tot_harga;
    TextView txt,tot_bayar;
    ImageView image_produk;
    FButton simpan_rekap,hitung,trams;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;
    SharedPrefManager_cs sharedPrefManager;
    String user_login;
    Spinner spin_pembeli, spin_produk,spin_id_trans;
    private ArrayList<String> tampil_pembeli;
    private ArrayList<String> tampil_produk;
    private ArrayList<String> tampil_id_trans;
    String ID_PRODUK,NAMA_PRODUK, ID_CUSTOMER,NAMA_CUSTOMER,id_trans;
    int hitung_tot_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rekapan);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Rekapan");

        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();
        Toast.makeText(add_rekapan.this,user_login,Toast.LENGTH_LONG).show();

        tampil_pembeli = new ArrayList<String>();
        tampil_produk = new ArrayList<String>();
        tampil_id_trans = new ArrayList<String>();
        spin_pembeli = findViewById(R.id.spin_pelanggan);
        spin_produk = findViewById(R.id.spin_produk);
        spin_id_trans = findViewById(R.id.spin_id_trans);

        txt = findViewById(R.id.txt);
        tot_bayar = findViewById(R.id.tot_bayar);
        add_harga = findViewById(R.id.add_harga);
        add_barang_beli = findViewById(R.id.add_barang_beli);
        tot_harga = findViewById(R.id.tot_harga);
        simpan_rekap = findViewById(R.id.simpan_rekapan);
        hitung = findViewById(R.id.hitung);
        trams = findViewById(R.id.trams);

        getproduk();
        spin_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCode=tampil_produk.get(position);
                String idproduk = parent.getItemAtPosition(position).toString();
                Toast.makeText(add_rekapan.this,selectedCode,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getcustomer();
        spin_pembeli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCode=tampil_pembeli.get(position);
                String idpembeli = parent.getItemAtPosition(position).toString();
                Toast.makeText(add_rekapan.this,selectedCode,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        getid_trans();
//        spin_id_trans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedCode=tampil_id_trans.get(position);
//                String idpembeli = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        trams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin_id_trans.setVisibility(View.VISIBLE);
                txt.setVisibility(View.VISIBLE);
                tampil_id_trans.clear();
                getid_trans();
            }
        });
        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int harga = Integer.parseInt(add_harga.getText().toString());
                int beli = Integer.parseInt(add_barang_beli.getText().toString());
                int hitung_tot_barang = harga * beli;
                tot_harga.setText(String.valueOf(hitung_tot_barang));
            }
        });

        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        simpan_rekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin_id_trans.getVisibility() == View.GONE){
                    loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
                    mApiService.add_dtl_transaksi(ID_PRODUK,add_barang_beli.getText().toString(),
                            tot_harga.getText().toString())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        loading.dismiss();
                                        Toast.makeText(add_rekapan.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        loading.dismiss();
                                        Toast.makeText(add_rekapan.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(add_rekapan.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    String ini_value = spin_id_trans.getSelectedItem().toString();
                    Toast.makeText(add_rekapan.this, ini_value, Toast.LENGTH_SHORT).show();
                    loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
                    mApiService.add_dtl_transaksi1(ini_value,ID_PRODUK,
                            add_barang_beli.getText().toString(),tot_harga.getText().toString())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        loading.dismiss();
                                        Toast.makeText(add_rekapan.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        loading.dismiss();
                                        Toast.makeText(add_rekapan.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(add_rekapan.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    public void simpan_transaksi(){
        loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
        mApiService.add_transaksi(spin_id_trans.getSelectedItem().toString(),
                tot_bayar.getText().toString(),ID_CUSTOMER,
                user_login)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(add_rekapan.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            loading.dismiss();
                            Toast.makeText(add_rekapan.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(add_rekapan.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getid_trans(){
        restManager = new RestManager();
        Call<List<Item>> listCall = restManager.ambil_data().getspiner_id_trans();
        //loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
        listCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (response.isSuccessful()) {
//                if(response.code() == 200) {
                    final List<Item> list_kegiatan = response.body();
//                    list_kegiatan.clear();
                    for (int i = 0; i < list_kegiatan.size(); i++) {
                        id_trans = list_kegiatan.get(i).getNO_TRANSAKSI();
                        tampil_id_trans.add(id_trans);

                        Log.d("hasilnya ", response.toString());
                        // loading.dismiss();
                    }
//                    tampil.add(0, "- pilih nip -");
                    spin_id_trans.setAdapter(new ArrayAdapter<String>(add_rekapan.this,
                            android.R.layout.simple_spinner_dropdown_item, tampil_id_trans));
                    spin_id_trans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
                            id_trans = list_kegiatan.get(position).getNO_TRANSAKSI();
//                            add_harga.setText(list_kegiatan.get(position).getHARGA());
//                            Toast.makeText(add_rekapan.this,ID_PRODUK, Toast.LENGTH_LONG).show();
                            if (selectedName != null)
                                spin_id_trans.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }/*else if (response.code() == 400)*/{
                    //loading.dismiss();
                }
//                }else {
//                    loading.dismiss();
//                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(add_rekapan.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
                Log.d("hasilnya ", t.toString());
            }
        });
    }


    public void getproduk(){
        restManager = new RestManager();
        Call<List<Item>> listCall = restManager.ambil_data().getspiner_produk();
        //loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
        listCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (response.isSuccessful()) {
//                if(response.code() == 200) {
                    final List<Item> list_kegiatan = response.body();
//                    list_kegiatan.clear();
                    for (int i = 0; i < list_kegiatan.size(); i++) {
                        Item datanya = list_kegiatan.get(i);
                        String nama_kls = list_kegiatan.get(i).getID_PRODUK();
                        ID_PRODUK = list_kegiatan.get(i).getID_PRODUK();
                        NAMA_PRODUK = list_kegiatan.get(i).getNAMA_PRODUK();
                        tampil_produk.add(NAMA_PRODUK);

                        Log.d("hasilnya ", response.toString());
                       // loading.dismiss();
                    }
//                    tampil.add(0, "- pilih nip -");
                    spin_produk.setAdapter(new ArrayAdapter<String>(add_rekapan.this,
                            android.R.layout.simple_spinner_dropdown_item, tampil_produk));
                    spin_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
                            //request data
                            ID_PRODUK = list_kegiatan.get(position).getID_PRODUK();
                            add_harga.setText(list_kegiatan.get(position).getHARGA());
                            Toast.makeText(add_rekapan.this,ID_PRODUK, Toast.LENGTH_LONG).show();
                            if (selectedName != null)
                                spin_produk.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }

            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(add_rekapan.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
                Log.d("hasilnya ", t.toString());
            }
        });
    }

    public void getcustomer(){
        restManager = new RestManager();
        Call<List<Item>> listCall = restManager.ambil_data().getspiner_customer();
        //loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
        listCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (response.isSuccessful()) {
//                if(response.code() == 200) {
                    final List<Item> list_kegiatan = response.body();
//                    list_kegiatan.clear();
                    for (int i = 0; i < list_kegiatan.size(); i++) {
                        Item datanya = list_kegiatan.get(i);
                        String nama_kls = list_kegiatan.get(i).getID_CUSTOMER();
                        ID_CUSTOMER = list_kegiatan.get(i).getID_CUSTOMER();
                        NAMA_CUSTOMER = list_kegiatan.get(i).getNAMA_CUSTOMER();
                        tampil_pembeli.add(NAMA_CUSTOMER);

                        Log.d("hasilnya ", response.toString());
                        // loading.dismiss();
                    }
//                    tampil.add(0, "- pilih nip -");
                    spin_pembeli.setAdapter(new ArrayAdapter<String>(add_rekapan.this,
                            android.R.layout.simple_spinner_dropdown_item, tampil_pembeli));
                    spin_pembeli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
                            //request data
                            ID_CUSTOMER = list_kegiatan.get(position).getID_CUSTOMER();
                            Toast.makeText(add_rekapan.this,ID_CUSTOMER, Toast.LENGTH_LONG).show();
                            if (selectedName != null)
                                spin_pembeli.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }/*else if (response.code() == 400)*/{
                    //loading.dismiss();
                }
//                }else {
//                    loading.dismiss();
//                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(add_rekapan.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
                Log.d("hasilnya ", t.toString());
            }
        });
    }


    public void get_total(){
        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        loading = ProgressDialog.show(add_rekapan.this, null, "Harap Tunggu...", true, false);
        Map<String, String> data = new HashMap<>();
        data.put("NO_TRANSAKSI", spin_id_trans.getSelectedItem().toString());
        mApiService.get_total_bayar(spin_id_trans.getSelectedItem().toString());
        Call<List<Item>> listCall = mApiService.get_total_bayar(spin_id_trans.getSelectedItem().toString());
        listCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                //jika ada
                if (response.isSuccessful()) {
//                if(response.code() == 200) {
                    List<Item> cari = response.body();
//                    list_kegiatan.clear();
                    for (int i = 0; i < cari.size(); i++) {
                        Item datanya = cari.get(i);
                        tot_bayar.setText(cari.get(i).getTotal_bayar());
                        loading.dismiss();
                    }
                    //jik tidak ada
                }/*else if (response.code() == 400)*/
                {
                    loading.dismiss();
                }
//                }else {
//                    loading.dismiss();
//                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(add_rekapan.this,t.toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(add_rekapan.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
                Log.d("hasilnya ", t.toString());
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.print){
            if (spin_id_trans.getVisibility() == View.GONE){
                Toast.makeText(add_rekapan.this,"pilih no transkasi dengan menekan tombol transaksi kembali"
                        ,Toast.LENGTH_LONG).show();
            }else {
                simpan_transaksi();
            }
            return true;
        }

        if (item.getItemId() == R.id.keranjang){
            if (spin_id_trans.getVisibility() == View.GONE){
                Toast.makeText(add_rekapan.this,"pilih no transkasi dengan menekan tombol transaksi kembali"
                        ,Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(add_rekapan.this, keranjang.class);
                intent.putExtra("kirim_no_trans", spin_id_trans.getSelectedItem().toString());
                startActivity(intent);
            }
            return true;
        }

        switch (item.getItemId()){
            case R.id.bayar:
                if (spin_id_trans.getVisibility() == View.GONE){
                    Toast.makeText(add_rekapan.this,"pilih no transkasi dengan menekan tombol transaksi kembali"
                            ,Toast.LENGTH_LONG).show();
                }else {
                    get_total();
                }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.refresh).setVisible(false);
//        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
}
