package com.developervishalsehgal.constraintlayoutchallenge.cs;

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
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_cari_rekapan;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_data_produk;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_data_rekap_pembelian;
import com.developervishalsehgal.constraintlayoutchallenge.adapter.adapter_keranjang;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RecyclerViewClickListener;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RecyclerViewTouchListener;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.VerticalLineDecorator;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class keranjang extends AppCompatActivity {
    String no_trans;
    private RecyclerView recyclerView;
    private RestManager restManager;
    private adapter_keranjang adapter;
    List<Item> dataproduk;
    apidata api;
    String TAG = "data produk - ";
    Context context;
    ProgressDialog loading;
    apidata mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Keranjang");

        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.list_keranjang);
        dataproduk = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        no_trans = bundle.getString("kirim_no_trans");
        Toast.makeText(keranjang.this,no_trans,Toast.LENGTH_LONG).show();

        adapter = new adapter_keranjang(this, dataproduk);
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

        api = restManager.createService(apidata.class);
        load(0);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Item kirim_id = adapter.getAmbildata(position);
                        Intent intent = new Intent(keranjang.this, update_rekapan.class);
                        intent.putExtra("kirim", kirim_id);
                        startActivity(intent);
                    }
                }));
    }

    private void load(int index){
        Call<List<Item>> call = api.getkeranjang(no_trans,index);
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

        Call<List<Item>> call = api.getkeranjang(no_trans,index);
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
}
