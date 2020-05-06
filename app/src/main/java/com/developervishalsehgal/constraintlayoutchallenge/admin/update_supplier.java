package com.developervishalsehgal.constraintlayoutchallenge.admin;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import info.hoang8f.widget.FButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_supplier extends AppCompatActivity {
    EditText update_nama_supplier, update_alamat, update_phone;
    FButton update_supplier;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;
    SharedPrefManager_cs sharedPrefManager;
    String user_login;
    Item dtlnya;
    String id_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Supplier");

        update_nama_supplier = findViewById(R.id.update_nama_supplier);
        update_alamat = findViewById(R.id.update_alamats);
        update_phone = findViewById(R.id.update_phones);
        update_supplier = findViewById(R.id.update_supplier);

        //ini maksudnya adalah menerima daya dari form pengirim
        //form pengirim di sini adalah form data ukuran
        //koding pengirimnya mana ?
        //ini jawabnnyanya akan saya tunjukan

        //apa itu kirim dan apa fungsinya ?
        //berikut penjelasannya
        Intent intent = getIntent();
        dtlnya = (Item) intent.getSerializableExtra("kirim");

        ///seharusnya tadi di sini
        //tapi gak apa apallah prosesnya sam aja kok
        id_supplier = dtlnya.getID_SUPPLIER();
        update_nama_supplier.setText(dtlnya.getNAMA_SUPPLIER());
        update_alamat.setText(dtlnya.getALAMAT_SUPPLIER());
        update_phone.setText(dtlnya.getPHONE_SUPPLIER());


        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        update_supplier.setOnClickListener(new View.OnClickListener() {
            @Override

            //id_ukuran di dapatkan dari variabel idukuran
            public void onClick(View view) {
                loading = ProgressDialog.show(update_supplier.this, null, "Harap Tunggu...", true, false);
                mApiService.update_data_supplier(id_supplier, update_nama_supplier.getText().toString(), update_alamat.getText().toString(), update_phone.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(update_supplier.this, "Berhasil update data", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(update_supplier.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(update_supplier.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
