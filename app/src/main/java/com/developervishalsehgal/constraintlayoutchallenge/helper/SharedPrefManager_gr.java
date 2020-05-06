package com.developervishalsehgal.constraintlayoutchallenge.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fariz ramadhan.
 * website : www.farizdotid.com
 * github : https://github.com/farizdotid
 * linkedin : https://www.linkedin.com/in/farizramadhan/
 */


public class SharedPrefManager_gr {

    public static final String SP_gr_APP = "sppgr";

    public static final String SP_NAMA = "spNama";
    public static final String SP_nis = "nis_ks";
    public static final String SP_nip = "nip_gr";
    public static final String SP_EMAIL = "spEmail";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager_gr(Context context){
        sp = context.getSharedPreferences(SP_gr_APP, Context.MODE_PRIVATE);
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

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSP_nis(){
        return sp.getString(SP_nis, "");
    }

    public String getSP_nip(){
        return sp.getString(SP_nip, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
