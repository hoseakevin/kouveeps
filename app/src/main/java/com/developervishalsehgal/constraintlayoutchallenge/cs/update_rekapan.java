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
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.admin.update_produk;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_gr;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_rekapan extends AppCompatActivity {
    EditText add_harga, add_barang_beli,tot_harga;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;
    SharedPrefManager_cs sharedPrefManager;
    String user_login;
    Item dtlnya;
    String idtrans;
    FButton simpan_rekap,hitung;
    Spinner spin_pembeli, spin_produk;
    private ArrayList<String> tampil_pembeli;
    private ArrayList<String> tampil_produk;
    String ID_PRODUK,NAMA_PRODUK, ID_CUSTOMER,NAMA_CUSTOMER,id_trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rekapan);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Rekapan");
        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();

        Intent intent = getIntent();
        dtlnya = (Item) intent.getSerializableExtra("kirim");
        idtrans = dtlnya.getID_DETIL_TRANSAKSI_PRODUK();
        Toast.makeText(update_rekapan.this, idtrans, Toast.LENGTH_SHORT).show();

        tampil_pembeli = new ArrayList<String>();
        tampil_produk = new ArrayList<String>();
        spin_pembeli = findViewById(R.id.spin_pelanggan);
        spin_produk = findViewById(R.id.spin_produk);

        add_harga = findViewById(R.id.add_harga);
        add_barang_beli = findViewById(R.id.add_barang_beli);
        tot_harga = findViewById(R.id.tot_harga);
        simpan_rekap = findViewById(R.id.simpan_rekapan);
        hitung = findViewById(R.id.hitung);

        getproduk();
        spin_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCode=tampil_produk.get(position);
                String idproduk = parent.getItemAtPosition(position).toString();
                Toast.makeText(update_rekapan.this,selectedCode,Toast.LENGTH_LONG).show();
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
                Toast.makeText(update_rekapan.this,selectedCode,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        simpan_rekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(update_rekapan.this, null, "Harap Tunggu...", true, false);
                mApiService.update_rekap(idtrans, ID_PRODUK, add_barang_beli.getText().toString(),
                        tot_harga.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(update_rekapan.this, "Berhasil update data", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(update_rekapan.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(update_rekapan.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                    spin_produk.setAdapter(new ArrayAdapter<String>(update_rekapan.this,
                            android.R.layout.simple_spinner_dropdown_item, tampil_produk));
                    spin_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
                            //request data
                            ID_PRODUK = list_kegiatan.get(position).getID_PRODUK();
                            add_harga.setText(list_kegiatan.get(position).getHARGA());
                            Toast.makeText(update_rekapan.this,ID_PRODUK, Toast.LENGTH_LONG).show();
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
                Toast.makeText(update_rekapan.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
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
                    spin_pembeli.setAdapter(new ArrayAdapter<String>(update_rekapan.this,
                            android.R.layout.simple_spinner_dropdown_item, tampil_pembeli));
                    spin_pembeli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
                            //request data
                            ID_CUSTOMER = list_kegiatan.get(position).getID_CUSTOMER();
                            Toast.makeText(update_rekapan.this,ID_CUSTOMER, Toast.LENGTH_LONG).show();
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
                Toast.makeText(update_rekapan.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.findItem(R.id.load).setVisible(false);
//        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

}
