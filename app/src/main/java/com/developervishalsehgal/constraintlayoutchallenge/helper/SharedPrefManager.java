package com.developervishalsehgal.constraintlayoutchallenge.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fariz ramadhan.
 * website : www.farizdotid.com
 * github : https://github.com/farizdotid
 * linkedin : https://www.linkedin.com/in/farizramadhan/
 */


public class SharedPrefManager {

    public static final String SP_kurikulum_APP = "sp_kurikulum";
    public static final String SP_pass = "pass";
    public static final String SP_NAMA = "spNama";
    public static final String SP_nis = "nis";
    public static final String SP_admin = "admin";
    public static final String SP_EMAIL = "spEmail";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_kurikulum_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String  getSP_pass() {
        return sp.getString(SP_pass, "");
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSP_nis(){
        return sp.getString(SP_nis, "");
    }

    public String getSP_admin(){
        return sp.getString(SP_admin, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
