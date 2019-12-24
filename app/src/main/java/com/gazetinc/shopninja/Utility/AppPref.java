package com.gazetinc.shopninja.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.gazetinc.shopninja.ShopninjaApplication;

import static android.content.Context.MODE_PRIVATE;

public class AppPref {

    private static AppPref instance;
    private final String NAME = "name";
    private final String ID="id";
    private final String EMAIL="email";
    private final String MOBILE="mobile";
    private final String VERIFIED="verified";


    private SharedPreferences sPreferences;
    private SharedPreferences.Editor sEditor;
    private String SG_SHARED_PREFERENCES = "shared_preferences";


    private AppPref(Context context) {

        sPreferences = context.getSharedPreferences(SG_SHARED_PREFERENCES,
                MODE_PRIVATE);
        sEditor = sPreferences.edit();
    }


    public static AppPref getInstance() {
        if (instance == null) {
            synchronized (AppPref.class) {
                if (instance == null) {
                    instance = new AppPref(ShopninjaApplication.getInstance().getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void registerPre(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sPreferences.registerOnSharedPreferenceChangeListener(listener);
    }


    public void unRegister(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


    public String getUSERID() {
        return sPreferences.getString(ID, "");
    }

    public void setUSERID(String id) {
        sEditor.putString(ID, id);
        sEditor.commit();
    }

    public String getNAME() {
        return sPreferences.getString(NAME, "");
    }

    public void setNAME(String name) {
        sEditor.putString(NAME, name);
        sEditor.commit();
    }

    public String getEMAIL() {
        return sPreferences.getString(EMAIL, "");
    }

    public void setEMAIL(String email) {
        sEditor.putString(EMAIL, email);
        sEditor.commit();
    }


    public String getVerified() {
        return sPreferences.getString(VERIFIED, "");
    }

    public void setVERIFIED(String verfied) {
        sEditor.putString(VERIFIED, verfied);
        sEditor.commit();
    }


    public String getMOBILE() {
        return sPreferences.getString(MOBILE, "");
    }

    public void setMOBILE(String mobile) {
        sEditor.putString(MOBILE, mobile);
        sEditor.commit();
    }
}
