package com.gazetinc.shopninja.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppUrl;
import com.gazetinc.shopninja.Utility.CustomEditText;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, ApiResponse
{
    private CustomEditText email;
    private Button btn_get_otp;

    @Override
    public void initialize(Bundle save) {

        email=(CustomEditText) findViewById(R.id.email_id);

        ImageView imageView1=(ImageView) email.findViewById(R.id.drawableleft);
        imageView1.setImageResource(R.mipmap.ic_user);

        btn_get_otp=(Button) findViewById(R.id.btn_get_otp);
        btn_get_otp.setOnClickListener(this);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_forgot;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_get_otp:
                sendtoserver();
                break;
        }
    }

    private void sendtoserver()
    {
        try {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("email=").append(email.getValue().toString());

            String content = stringBuilder.toString();

            ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.FORGET_PASSWORD, 0, content, true, "Logging ...", this);
            apiConsumer.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try
        {
            Log.d("responsedata",responseData);
            if(responseData.equalsIgnoreCase("sent"))
            {
                toastMessage("Password sent on your Email Id");
                finish();
            }
            else
                toastMessage("Mail Not Found");

        }catch (Exception e)
        {
            toastMessage("Some Error Occurred");
            e.printStackTrace();
        }
    }
}

