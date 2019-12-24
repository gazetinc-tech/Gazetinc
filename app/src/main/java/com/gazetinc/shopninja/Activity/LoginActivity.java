package com.gazetinc.shopninja.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppPref;
import com.gazetinc.shopninja.Utility.AppUrl;
import com.gazetinc.shopninja.Utility.CustomEditText;
import com.gazetinc.shopninja.Utility.UtilityClass;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ApiResponse
{
    private TextView create_account;
    private TextView forget_password;
    private Button login;

    private CustomEditText edt_username,edt_password;

    @Override
    public void initialize(Bundle save) {
        create_account=(TextView) findViewById(R.id.create_account);
        forget_password=(TextView) findViewById(R.id.forgot_password);

        edt_username=(CustomEditText) findViewById(R.id.edt_email);
        edt_password=(CustomEditText) findViewById(R.id.edt_password);

        login=(Button) findViewById(R.id.login);

        create_account.setPaintFlags(create_account.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        forget_password.setPaintFlags(forget_password.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        create_account.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.create_account:
                sendToActivity(RegisterActivity.class);
                break;
            case R.id.forgot_password:
                sendToActivity(ForgetPasswordActivity.class);
                break;
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        edt_password.hideError();
        edt_username.hideError();
        String string_username = edt_username.getValue().toString();
        String string_password = edt_password.getValue().toString();

        if (string_username.equals("")) {
            String s = "Please Enter Email Id/Number";
            edt_username.setError(s);
            return;
        }
        if (string_username.contains(".")) {
            if (!UtilityClass.isValidEmail(string_username)) {
                edt_username.setError("Please Enter Valid Email");
                return;
            }
        } else {
            if (string_username.matches("[0-9]+") && string_username.length() < 10) {
                edt_username.setError("Please Enter Valid Mobile Number");
                return;
            }
            if (string_username.startsWith("0") || string_username.startsWith("1") || string_username.startsWith("2") ||
                    string_username.startsWith("3") || string_username.startsWith("4") || string_username.startsWith("5")) {
                edt_username.setError("Please Enter Valid Mobile Number");
                return;
            } else if (!string_username.matches("[0-9]+") && string_username.length() < 10 && !UtilityClass.isValidEmail(string_username)) {
                edt_username.setError("Invalid Email Id");
                return;
            }
        }
        if (string_password.equals("")) {
            edt_password.setError("Please Enter Password");
            return;
        }

        try {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("email=").append(string_username);
            stringBuilder.append("&password=").append(string_password);

            String content = stringBuilder.toString();

            ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.LOGIN_URL, 0, content, true, "Logging ...", this);
            apiConsumer.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try{
            Log.d("responsedata",responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            jsonObject=jsonObject.getJSONObject("login");

            String name=jsonObject.getString("name");
            String email=jsonObject.getString("email");
            String mobile=jsonObject.getString("mobile");
            String id=jsonObject.getString("id");
            String status=jsonObject.getString("status");


                AppPref.getInstance().setUSERID(id);
                AppPref.getInstance().setNAME(name);
                AppPref.getInstance().setEMAIL(email);
                AppPref.getInstance().setMOBILE(mobile);
                AppPref.getInstance().setVERIFIED(status);

                if(status.equalsIgnoreCase("1")) {
                    sendToActivity(MainActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
                else
                    sendToActivity(OtpActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
