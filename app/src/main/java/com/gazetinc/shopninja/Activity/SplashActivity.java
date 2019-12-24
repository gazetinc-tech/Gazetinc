package com.gazetinc.shopninja.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.AppPref;

public class SplashActivity extends BaseActivity
{
    @Override
    public void initialize(Bundle save) {
       getSupportActionBar().hide();
       Handler handler=new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               decision();
           }
       },2000);
    }

    private void decision()
    {
        if(AppPref.getInstance().getUSERID().equalsIgnoreCase(""))
        {
            sendToActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        else if(AppPref.getInstance().getVerified().equalsIgnoreCase("") || AppPref.getInstance().getVerified().equalsIgnoreCase("0"))
        {
            sendToActivity(OtpActivity.class,Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        else
            sendToActivity(MainActivity.class,Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_splash;
    }
}
