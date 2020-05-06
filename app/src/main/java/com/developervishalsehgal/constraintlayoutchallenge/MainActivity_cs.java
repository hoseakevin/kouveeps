package com.developervishalsehgal.constraintlayoutchallenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.cs.data_customer;
import com.developervishalsehgal.constraintlayoutchallenge.cs.rekap_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;

public class MainActivity_cs extends AppCompatActivity {
    SharedPrefManager_cs sharedPrefManager;
    String user_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cs);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar1);
//        toolbar1.setTitleTextColor(Color.WHITE);
//        toolbar1.setSubtitleTextColor(Color.WHITE);
//        getSupportActionBar().setTitle("kepala sekolah");
        sharedPrefManager = new SharedPrefManager_cs(this);
        user_login = sharedPrefManager.getSP_nip();
        Toast.makeText(MainActivity_cs.this, sharedPrefManager.getSP_nip(), Toast.LENGTH_LONG).show();
    }
    public void onButtonClick(View view) {
        int id = view.getId();

        Intent intent = null;
        switch (id) {
            case R.id.logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity_cs.this);
                alertDialogBuilder.setMessage("apakah anda yakin ingin keluar ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                startActivity(new Intent(MainActivity_cs.this, login.class)
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

            case R.id.penjualan:
                intent = new Intent(MainActivity_cs.this, rekap_cs.class);
                startActivity(intent);
                break;

            case R.id.customer:
                intent = new Intent(MainActivity_cs.this, data_customer.class);
                startActivity(intent);
                break;
//
//            case R.id.panduan:
////                intent = new Intent(MainActivity_cs.this, rekap_cs.class);
////                startActivity(intent);
//                break;
        }
    }
}

