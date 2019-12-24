package com.gazetinc.shopninja.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.AppPref;


public class ShareActivity extends BaseActivity implements View.OnClickListener{
    private TextView free_ride_tv_referralCode;

    private ImageButton imgBtnWhatsApp;
    private ImageButton imgBtnFacebook;
    private ImageButton imgBtnMessage;
    private Button btnInviteFriend;

    private String shareText="Sign up with directpe & use my referral code : (";



    @Override
    public void initialize(Bundle save) {
        setTitle("Refer & Earn");
     //   shareText+= AppPref.getInstance().getGENCODE()+")\n Link : https://play.google.com/store/apps/details?id=com.direct.pe";


        shareText=Html.fromHtml(shareText).toString();

        free_ride_tv_referralCode=(TextView) findViewById(R.id.free_ride_tv_referralCode);
//        free_ride_tv_referralCode.setText(AppPref.getInstance().getGENCODE());

        imgBtnWhatsApp = (ImageButton) findViewById(R.id.free_ride_imgbtn_whatsapp);
        imgBtnWhatsApp.setOnClickListener(this);
        imgBtnFacebook = (ImageButton) findViewById(R.id.free_ride_imgbtn_facebook);
        imgBtnFacebook.setOnClickListener(this);
        imgBtnMessage = (ImageButton) findViewById(R.id.free_ride_imgbtn_message);
        imgBtnMessage.setOnClickListener(this);
        btnInviteFriend = (Button) findViewById(R.id.free_ride_btn_invite_friend);
        btnInviteFriend.setOnClickListener(this);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.free_ride_imgbtn_whatsapp:
                try {
                    sendMessage(shareText, "com.whatsapp");
                } catch (Exception e) {
                }
                break;

            case R.id.free_ride_imgbtn_facebook:
                try {
                    sendMessage(shareText, "com.facebook.katana");
                } catch (Exception e) {
                }
                break;

            case R.id.free_ride_imgbtn_message:
                Uri uri = Uri.parse("smsto:");
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                smsIntent.putExtra("sms_body", shareText);
                startActivity(smsIntent);
                break;

            case R.id.free_ride_btn_invite_friend:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Shopninja");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sharingIntent, "share"));
                break;
        }

    }

    private void sendMessage(String message, String packageName) throws Exception {
        Intent sendIntent = new Intent();
        sendIntent.setPackage(packageName);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
