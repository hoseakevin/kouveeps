package com.developervishalsehgal.constraintlayoutchallenge.admin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RealPathUtil;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import info.hoang8f.widget.FButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_layanan extends AppCompatActivity {
    EditText add_nama_layanan, harga_layanan;
    FButton simpan_layanan;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;
    SharedPrefManager_cs sharedPrefManager;
    String user_login, id_ukuran, id_jenishewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_layanan);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarl);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Layanan");

        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();

        add_nama_layanan = findViewById(R.id.add_nama_layanan);
        harga_layanan = findViewById(R.id.add_hargal);
        simpan_layanan = findViewById(R.id.simpan_layanan);

        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        simpan_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(add_layanan.this, null, "Harap Tunggu...", true, false);
                mApiService.add_layanan(add_nama_layanan.getText().toString(), harga_layanan.getText().toString(),id_ukuran,id_jenishewan)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(add_layanan.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(add_layanan.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(add_layanan.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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
