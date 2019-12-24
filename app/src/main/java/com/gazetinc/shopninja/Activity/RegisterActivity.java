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

public class RegisterActivity extends BaseActivity implements View.OnClickListener, ApiResponse
{
    private TextView terms_text;

    private CustomEditText edt_name,edt_mobile,edt_password,edt_email,edt_referal;

    private Button login;

    @Override
    public void initialize(Bundle save) {
        edt_name = (CustomEditText) findViewById(R.id.edt_name);
        edt_mobile = (CustomEditText) findViewById(R.id.edt_mobile);
        edt_email = (CustomEditText) findViewById(R.id.edt_email);
        edt_password = (CustomEditText) findViewById(R.id.edt_password);
        edt_referal=(CustomEditText) findViewById(R.id.edt_refer_code);


        edt_mobile.setFilters(10);

        terms_text=(TextView) findViewById(R.id.terms_text);
        login=(Button) findViewById(R.id.login);

        terms_text.setPaintFlags(terms_text.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        terms_text.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.terms_text:
                sendToActivity(TermsandConditionsActivity.class);
                break;
            case R.id.login:
                register();
                break;
        }
    }

    private void register()
    {
        edt_name.hideError();
        edt_mobile.hideError();
        edt_email.hideError();
        edt_password.hideError();

        String string_name = edt_name.getValue().toString();
        String string_mobile = edt_mobile.getValue().toString();
        String string_email = edt_email.getValue().toString();
        String string_password = edt_password.getValue().toString();


        if (string_name.equalsIgnoreCase("")) {
            edt_name.setError("Please Enter Name");
            return;
        }
        if (string_mobile.equalsIgnoreCase("")) {
            edt_mobile.setError("Please Enter Mobile Number");
            return;
        } else {
            if (string_mobile.length() < 10 || string_mobile.length() > 10) {
                edt_mobile.setError("Please enter the valid mobile number");
                return;
            }
            if (string_mobile.startsWith("0") || string_mobile.startsWith("1") || string_mobile.startsWith("2") ||
                    string_mobile.startsWith("3") || string_mobile.startsWith("4") || string_mobile.startsWith("5")) {
                edt_mobile.setError("Please enter the valid mobile number");
                return;
            }
        }
        if (string_email.equals("")) {
            edt_email.setError("Please Enter E-mail Id");
            return;
        } else {
            if (!UtilityClass.isValidEmail(string_email)) {
                edt_email.setError("Please enter valid email ID");
                return;
            }
        }
        if (string_password.equalsIgnoreCase("")) {
            edt_password.setError("Please Enter Password");
            return;
        } else {
            if (string_password.length() < 6) {
                edt_password.setError("Password must be atleast 6 characters long");
                return;
            }
        }


        try {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("name=").append(string_name);
            stringBuilder.append("&email=").append(string_email);
            stringBuilder.append("&mobile=").append(string_mobile);
            stringBuilder.append("&password=").append(string_password);


            String content = stringBuilder.toString();

            Log.d("content", content);

            ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.REGISTER_URL, 0, content, true, "Registering ...", this);
            apiConsumer.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        Log.d("responsedata",responseData);
        try
        {
            JSONObject jsonObject=new JSONObject(responseData);
            String user_id=jsonObject.getString("id");

            AppPref.getInstance().setUSERID(user_id);
            AppPref.getInstance().setNAME(edt_name.getValue());
            AppPref.getInstance().setEMAIL(edt_email.getValue());
            AppPref.getInstance().setMOBILE(edt_mobile.getValue());

            sendToActivity(OtpActivity.class);
        }
        catch (Exception e)
        {
            toastMessage(responseData);
        }
    }
}
