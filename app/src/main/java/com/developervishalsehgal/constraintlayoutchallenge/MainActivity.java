package com.developervishalsehgal.constraintlayoutchallenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.admin.data_layanan;
import com.developervishalsehgal.constraintlayoutchallenge.admin.data_supplier;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager;
import com.developervishalsehgal.constraintlayoutchallenge.admin.data_produk;

public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    String user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar1);
//        toolbar1.setTitleTextColor(Color.WHITE);
//        toolbar1.setSubtitleTextColor(Color.WHITE);
//        getSupportActionBar().setTitle("penilaian");
        sharedPrefManager = new SharedPrefManager(this);
        user_login = sharedPrefManager.getSP_admin();
        Toast.makeText(MainActivity.this, sharedPrefManager.getSP_admin(), Toast.LENGTH_LONG).show();
    }

    public void onButtonClick(View view) {
        int id = view.getId();

        Intent intent = null;
        switch (id) {
            case R.id.logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("apakah anda yakin ingin keluar ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                startActivity(new Intent(MainActivity.this, login.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;

            case R.id.layanan:
                intent = new Intent(MainActivity.this, data_layanan.class);
                startActivity(intent);
                break;

            case R.id.produk:
                intent = new Intent(MainActivity.this, data_produk.class);
                startActivity(intent);
                break;

            case R.id.supplier:
                intent = new Intent(MainActivity.this, data_supplier.class);
                startActivity(intent);
                break;

        }
    }
}
