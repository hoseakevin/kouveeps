package com.developervishalsehgal.constraintlayoutchallenge.cs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_cari_produk;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_cari_rekapan;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_data_produk;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_data_rekap_pembelian;
import com.developervishalsehgal.constraintlayoutchallenge.admin.add_produk;
import com.developervishalsehgal.constraintlayoutchallenge.admin.data_produk;
import com.developervishalsehgal.constraintlayoutchallenge.admin.update_produk;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RecyclerViewClickListener;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RecyclerViewTouchListener;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.VerticalLineDecorator;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class rekap_cs extends AppCompatActivity implements adapter_cari_rekapan.data_kirim{
    SharedPrefManager_cs sharedPrefManager;
    String user_login;
    private RecyclerView recyclerView;
    private RestManager restManager;
    private adapter_data_rekap_pembelian adapter;
    private adapter_cari_rekapan adapter_cari;
    List<Item> dataproduk;
    apidata api;
    String TAG = "data produk - ";
    Context context;
    FloatingActionButton fab;
    FButton cari;
    ProgressDialog loading;
    apidata mApiService;
    EditText cari_pembeli;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog fromDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_cs);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rekap Penjualan");
        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();

        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.list_rekap_penjualam);
        dataproduk = new ArrayList<>();
        cari_pembeli = findViewById(R.id.cari_pembeli);

        adapter = new adapter_data_rekap_pembelian(this, dataproduk);
        adapter_cari = new adapter_cari_rekapan(this);
        adapter.setLoadMoreListener(new adapter_data_produk.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = dataproduk.size() - 0;
                        loadMore(index);
                    }
                });
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        recyclerView.setAdapter(adapter);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        cari_pembeli = findViewById(R.id.cari_pembeli);
        cari_pembeli.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        api = restManager.createService(apidata.class);
        load(0);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Item kirim_id = adapter.getAmbildata(position);
                        Intent intent = new Intent(rekap_cs.this, update_rekapan.class);
                        intent.putExtra("kirim", kirim_id);
                        startActivity(intent);
                    }
                }));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(rekap_cs.this, add_rekapan.class);
                startActivity(in);
            }
        });

        cari = findViewById(R.id.cari);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cari_pembeli.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(rekap_cs.this, "silahkan klik cari pertanggal", Toast.LENGTH_LONG).show();
                }else {
                    adapter_cari.clear();
                    recyclerView.setAdapter(adapter_cari);
                    getcari_rekapan();
                }
            }
        });
    }

    private void load(int index){
        Call<List<Item>> call = api.getdata_rekap(user_login,index);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){
                    dataproduk.addAll(response.body());
                    adapter.notifyDataChanged();
                }else{
                    Log.e(TAG," Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });
    }

    private void loadMore(int index){

        //add loading progress view
        dataproduk.add(new Item("NO_TRANSAKSI"));
        adapter.notifyItemInserted(dataproduk.size()-1);

        Call<List<Item>> call = api.getdata_rekap(user_login,index);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){

                    //remove loading view
                    dataproduk.remove(dataproduk.size()-1);

                    List<Item> result = response.body();
                    if(result.size()>0){
                        //add loaded data
                        dataproduk.addAll(result);
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
            }
        });
    }

    public void getcari_rekapan(){
        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        loading = ProgressDialog.show(rekap_cs.this, null, "Harap Tunggu...", true, false);
        Map<String, String> data = new HashMap<>();
        data.put("ID_PEGAWAI", user_login);
        data.put("TANGGAL_TRANSAKSI", cari_pembeli.getText().toString());
        mApiService.getcari_rekapan(user_login, cari_pembeli.getText().toString());
        Call<List<Item>> listCall = mApiService.getcari_rekapan(user_login, cari_pembeli.getText().toString());
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
                        adapter_cari.adddata(datanya);
                        Toast.makeText(rekap_cs.this, response.toString(), Toast.LENGTH_LONG).show();
                        Log.d("hasilnya ", response.toString());
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
                Toast.makeText(rekap_cs.this,t.toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(rekap_cs.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
                Log.d("hasilnya ", t.toString());
            }
        });
    }

    private void setDateTimeField() {
        cari_pembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cari_pembeli.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cari_pembeli.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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

        switch (item.getItemId()){
            case R.id.refresh:
                adapter.clear();
                load(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.bayar).setVisible(false);
        menu.findItem(R.id.print).setVisible(false);
        menu.findItem(R.id.keranjang).setVisible(false);
//        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public void onClick(int position) {

    }
}
