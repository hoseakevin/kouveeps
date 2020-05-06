package com.developervishalsehgal.constraintlayoutchallenge.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;

import info.hoang8f.widget.FButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_supplier extends AppCompatActivity {
    EditText add_nama_supplier, add_alamat, add_phone;
    FButton simpan_supplier;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;
    SharedPrefManager_cs sharedPrefManager;
    String user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Supplier");

        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();

        add_nama_supplier = findViewById(R.id.add_nama_supplier);
        add_alamat = findViewById(R.id.add_alamat);
        add_phone = findViewById(R.id.add_phone);
        simpan_supplier = findViewById(R.id.simpan_supplier);

        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        simpan_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(add_supplier.this, null, "Harap Tunggu...", true, false);
                mApiService.add_supplier(add_nama_supplier.getText().toString(), add_alamat.getText().toString(),
                        add_phone.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(add_supplier.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(add_supplier.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(add_supplier.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
