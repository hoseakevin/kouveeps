package com.developervishalsehgal.constraintlayoutchallenge.cs;

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

public class add_customer extends AppCompatActivity {
    EditText add_nama_customer, add_alamat, add_phone, add_tanggal;
    FButton simpan_customer;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;
    SharedPrefManager_cs sharedPrefManager;
    String user_login,id_pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarc);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Customer");

        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();

        add_nama_customer = findViewById(R.id.add_nama_customer);
        add_alamat = findViewById(R.id.add_alamatc);
        add_phone = findViewById(R.id.add_phonec);
        add_tanggal = findViewById(R.id.add_tanggal_lahirc);
        simpan_customer = findViewById(R.id.simpan_customer);


        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        simpan_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(add_customer.this, null, "Harap Tunggu...", true, false);
                mApiService.add_customer(add_nama_customer.getText().toString(), add_tanggal.getText().toString(), add_alamat.getText().toString(),
                        add_phone.getText().toString(), id_pegawai)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(add_customer.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(add_customer.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(add_customer.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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
