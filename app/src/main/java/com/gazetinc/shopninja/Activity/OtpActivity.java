package com.gazetinc.shopninja.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppPref;
import com.gazetinc.shopninja.Utility.AppUrl;
import com.gazetinc.shopninja.Utility.CustomEditText;
import com.mukesh.OtpView;

import org.json.JSONObject;

import java.util.Random;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse
{

    private OtpView otpView;
    private Button validate_button;
    private TextView number_t;

    private String number="";
    private String otp="";

    private TextView resend;

    public void onCreate(Bundle save)
    {
        super.onCreate(save);
        setContentView(getActivityLayout());
        initialize(save);
    }

    public void initialize(Bundle save) {

        validate_button=(Button) findViewById(R.id.validate_button);
        number_t=(TextView) findViewById(R.id.number_t);

        resend=(TextView) findViewById(R.id.resend);

        resend.setPaintFlags(resend.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        number= AppPref.getInstance().getMOBILE();
        number_t.setText(number);


        otpView = findViewById(R.id.otp_view);

        getOtp();


        validate_button.setOnClickListener(this);
        resend.setOnClickListener(this);

        number_t.setOnClickListener(this);
    }


    private void getOtp()
    {
        int n = 1000 + new Random().nextInt(9000);
        otp=""+n;
        String url="https://api.textlocal.in/send/?apiKey=tkQ6xgg3zvw-F83crQ2JoGmtRxxqqV3YaVtBBEjyhz" +
                "&sender=DIRCPE" +
                "&numbers=" +number+
                "&message=Your%20OTP%20is%20"+otp;

        Log.d("opturl",url);

        ApiConsumer apiConsumer=new ApiConsumer(this,url,0,"",true,"loading ...",this);
        apiConsumer.execute();
    }


    public int getActivityLayout() {
        return R.layout.activity_otp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.resend:
                getOtp();
                break;
            case R.id.validate_button:
                String otpstring=otpView.getText().toString();
                if(otp.equalsIgnoreCase(otpstring))
                {
                    verifyotp();
                }
                break;
            case R.id.number_t:
                editNumber();
                break;
        }
    }

    private void editNumber()
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inf = LayoutInflater.from(this);
        View v1 = inf.inflate(R.layout.dialog_change_number, null);

        ImageView back = (ImageView) v1.findViewById(R.id.back);


        final CustomEditText edt_promo_code = (CustomEditText) v1.findViewById(R.id.edt_promo_code);

        edt_promo_code.setValue(AppPref.getInstance().getMOBILE());

        Button btn_apply_promocode = (Button) v1.findViewById(R.id.btn_apply_promocode);


        dialog.setView(v1);
        final Dialog d1 = dialog.create();
        d1.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d1.dismiss();
            }
        });
        btn_apply_promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_promo_code.getValue().equalsIgnoreCase("")) {
                    edt_promo_code.setError("Please Enter Number");
                    return;
                }

                AppPref.getInstance().setMOBILE(edt_promo_code.getValue());
                number=AppPref.getInstance().getMOBILE();
                number_t.setText(number);
                d1.dismiss();

            }
        });

    }


    private void verifyotp()
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("user_id=").append(AppPref.getInstance().getUSERID());
        stringBuilder.append("&mobile=").append(AppPref.getInstance().getMOBILE());

        String content=stringBuilder.toString();

        ApiConsumer apiConsumer=new ApiConsumer(this, AppUrl.VERIFY_USER, 0, content, true, "loading ...", new ApiResponse() {
            @Override
            public void getApiResponse(String responseData, int serviceCounter) {
                try{
                    Log.d("responsedata",responseData);
                    JSONObject jsonObject=new JSONObject(responseData);
                    String status=jsonObject.getString("status");
                    if(status.equalsIgnoreCase("1"))
                    {
                        AppPref.getInstance().setVERIFIED("1");
                        Intent intent=new Intent(OtpActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(OtpActivity.this,"Some Error Occurred",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(OtpActivity.this,"Some Error Occurred",Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        });
        apiConsumer.execute();
    }


    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try
        {
            Log.d("responsedata",responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            String status=jsonObject.getString("status");
            if(status.equalsIgnoreCase("success"))
            {
                Toast.makeText(this,"Otp Sent",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
