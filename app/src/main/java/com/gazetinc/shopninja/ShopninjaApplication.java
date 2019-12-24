package com.gazetinc.shopninja;

import android.app.Application;

import com.gazetinc.shopninja.Utility.TypefaceUtil;

public class ShopninjaApplication extends Application {
    private static ShopninjaApplication instance;

    public void onCreate()
    {
        super.onCreate();
        instance = this;
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Muli-Regular.ttf");
    }

    public static ShopninjaApplication getInstance() {
        if (instance == null) {
            instance = new ShopninjaApplication();
        }
        return instance;
    }
}
