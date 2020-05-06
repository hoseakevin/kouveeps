package com.developervishalsehgal.constraintlayoutchallenge.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_cari_supplier;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_data_supplier;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RecyclerViewClickListener;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RecyclerViewTouchListener;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.VerticalLineDecorator;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class data_supplier extends AppCompatActivity implements adapter_cari_supplier.data_kirim{
    private RecyclerView recyclerView;
    private RestManager restManager;
    private adapter_data_supplier adapter;
//    private adapter_cari_supplier adapter_cari;
    List<Item> dataSupplier;
    apidata api;
    String TAG = "data supplier - ";
    Context context;
    FloatingActionButton fab;
    FButton cari;
    ProgressDialog loading;
    apidata mApiService;
    EditText cari_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_supplier);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Supplier");

        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.list_data_supplier);
        dataSupplier = new ArrayList<>();
        cari_supplier = findViewById(R.id.cari_supplier);

        adapter = new adapter_data_supplier(this, dataSupplier);
 //       adapter_cari = new adapter_cari_supplier(this);
        adapter.setLoadMoreListener(new adapter_data_supplier.OnLoadMoreListener(){
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = dataSupplier.size() - 0;
                        loadMore(index);
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        recyclerView.setAdapter(adapter);

        api = restManager.createService(apidata.class);
        load(0);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Item kirim_id = adapter.getAmbildata(position);
                        Intent intent = new Intent(data_supplier.this, update_supplier.class);
                        intent.putExtra("kirim", kirim_id);
                        startActivity(intent);
                    }
                }));

        fab = findViewById(R.id.fabs);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(data_supplier.this, add_supplier.class);
                startActivity(in);
            }
        });

        cari = findViewById(R.id.caris);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                recyclerView.setAdapter(adapter);
                getcari_supplier();
            }
        });
    }

    private void load(int index){
        Call<List<Item>> call = api.getdata_supplier(index);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){
                    dataSupplier.addAll(response.body());
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
        dataSupplier.add(new Item("ID_SUPPLIER"));
        adapter.notifyItemInserted(dataSupplier.size()-1);

        Call<List<Item>> call = api.getdata_supplier(index);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){

                    //remove loading view
//                    dataSupplier.remove(dataSupplier.size()-1);

                    List<Item> result = response.body();
                    if(result.size()>0){
                        //add loaded data
                        dataSupplier.addAll(result);
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

    public void getcari_supplier(){
        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        loading = ProgressDialog.show(data_supplier.this, null, "Harap Tunggu...", true, false);
        Map<String, String> data = new HashMap<>();
        data.put("NAMA_SUPPLIER", cari_supplier.getText().toString());
        mApiService.list_cari_supplier(cari_supplier.getText().toString());
        Call<List<Item>> listCall = mApiService.list_cari_supplier(cari_supplier.getText().toString());
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
                        adapter.adddata(datanya);
                        Toast.makeText(data_supplier.this, response.toString(), Toast.LENGTH_LONG).show();
                        Log.d("hasilnya ", response.toString());
                        loading.dismiss();
                    }
                    //jik tidak ada
                }/*else if (response.code() == 400)*/{
                    loading.dismiss();
                }
//                }else {
//                    loading.dismiss();
//                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//                }
            }

            //kesalahan jaringan
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(data_supplier.this,t.toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(data_supplier.this, "cek koneksi internet", Toast.LENGTH_SHORT).show();
                Log.d("hasilnya ", t.toString());
            }
        });
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
//        menu.findItem(R.id.load).setVisible(false);
//        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public void onClick(int position) {

    }
}
