package com.developervishalsehgal.constraintlayoutchallenge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_gr;
import com.developervishalsehgal.constraintlayoutchallenge.helper.SharedPrefManager_cs;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    EditText username,password;
    Button btnlogin;
    ProgressDialog loading;
    apidata mApiService;
    private RestManager restManager;
    SharedPrefManager sharedPrefManager;
    SharedPrefManager_cs cs;
    //    SharedPrefManager_gr sharedPrefManager_gr;
    Context mContext;
    String ambil_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.login);

        mContext = this;
        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        sharedPrefManager = new SharedPrefManager(this);
        cs = new SharedPrefManager_cs(this);
//        sharedPrefManager_gr = new SharedPrefManager_gr(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin_pegawai();
            }
        });

        if (sharedPrefManager.getSPSudahLogin()) {
            Intent in = new Intent(login.this, MainActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();
        }

        if (cs.getSPSudahLogin()) {
            Intent in = new Intent(login.this, MainActivity_cs.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();
        }
    }

    private void requestLogin_pegawai () {
        mApiService.loginpegawai(username.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")) {
                                    String jbtn = jsonRESULTS.getJSONObject("user").getString("JABATAN");
                                    loading.dismiss();
                                    Toast.makeText(mContext, jbtn, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                    if (jbtn.equalsIgnoreCase("admin")) {
                                        sharedPrefManager.saveSPString(sharedPrefManager.SP_NAMA, username.getText().toString());
                                        sharedPrefManager.saveSPString(sharedPrefManager.SP_admin, username.getText().toString());
//                                sharedPrefManager.saveSPString(SharedPrefManager.SP_jab, jabatan);
                                        sharedPrefManager.saveSPBoolean(sharedPrefManager.SP_SUDAH_LOGIN, true);
                                        Intent in = new Intent(login.this, MainActivity.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                    } else {
//                            sharedPrefManager_cs.saveSPString(sharedPrefManager_cs.SP_NAMA, username.getText().toString());
                                        cs.saveSPString(cs.SP_nip, username.getText().toString());
//                                sharedPrefManager.saveSPString(SharedPrefManager.SP_jab, jabatan);
                                        cs.saveSPBoolean(cs.SP_SUDAH_LOGIN, true);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity_cs.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Log.d("hasilnya ", response.message().toString());
                                    }
                                } else {

                                    Toast.makeText(mContext, "GAGAL LOGIN", Toast.LENGTH_SHORT).show();
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, "GAGAL LOGIN", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });

    }
}
