package com.gazetinc.shopninja.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gazetinc.shopninja.Fragment.HomeFragment;
import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppPref;
import com.gazetinc.shopninja.Utility.AppUrl;
import com.gazetinc.shopninja.Utility.CustomEditText;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentDemo extends BaseActivity implements View.OnClickListener, PaymentResultListener,ApiResponse
{
    private TextView balance;
    private TextView txt_1500;
    private TextView txt_2000;
    private TextView txt_2500;
    private TextView promo_code;

    private CustomEditText edt_wallet;

    private Button btn_add_money;

    @Override
    public void initialize(Bundle save) {
        setTitle("Wallet");
        balance=(TextView) findViewById(R.id.balance);
        edt_wallet=(CustomEditText) findViewById(R.id.edt_wallet);

        btn_add_money=(Button) findViewById(R.id.btn_add_money);

        promo_code=(TextView) findViewById(R.id.promo_code);
        txt_1500=(TextView) findViewById(R.id.txt_1500);
        txt_2000=(TextView) findViewById(R.id.txt_2000);
        txt_2500=(TextView) findViewById(R.id.txt_2500);

        txt_1500.setText("\u20B9 " + 1500);
        txt_2000.setText("\u20B9 " + 2000);
        txt_2500.setText("\u20B9 " + 2500);

        promo_code.setPaintFlags(txt_2500.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);




        txt_2500.setOnClickListener(this);
        txt_1500.setOnClickListener(this);
        txt_2000.setOnClickListener(this);

        btn_add_money.setOnClickListener(this);
        promo_code.setOnClickListener(this);

        edt_wallet.setValue("1500");
        txt_1500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_like));
        txt_1500.setTextColor(getResources().getColor(R.color.white));
        txt_2500.setTextColor(getResources().getColor(R.color.home_text_color));
        txt_2000.setTextColor(getResources().getColor(R.color.home_text_color));
        txt_2000.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
        txt_2500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));

        Checkout.preload(getApplicationContext());

        getWalletBalance();
    }

    @Override
    public int getActivityLayout() {
        return R.layout.fragment_wallet;
    }

    private void getWalletBalance()
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("user_id=").append(AppPref.getInstance().getUSERID());

        String content=stringBuilder.toString();

        ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.GET_WALLET, 0, content, true, "loading ...", this);
        apiConsumer.execute();
    }


    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try
        {
            Log.d("responsedata",responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            String wallet=jsonObject.getString("wallet");
            balance.setText("\u20B9 "+wallet);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_1500:
                edt_wallet.setValue("1500");
                txt_1500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_like));
                txt_1500.setTextColor(getResources().getColor(R.color.white));
                txt_2500.setTextColor(getResources().getColor(R.color.home_text_color));
                txt_2000.setTextColor(getResources().getColor(R.color.home_text_color));
                txt_2000.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
                txt_2500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
                break;
            case R.id.txt_2000:
                edt_wallet.setValue("2000");
                txt_1500.setTextColor(getResources().getColor(R.color.home_text_color));
                txt_2500.setTextColor(getResources().getColor(R.color.home_text_color));
                txt_2000.setTextColor(getResources().getColor(R.color.white));
                txt_1500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
                txt_2000.setBackground(getResources().getDrawable(R.drawable.rounded_corner_like));
                txt_2500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
                break;
            case R.id.txt_2500:
                edt_wallet.setValue("2500");
                txt_1500.setTextColor(getResources().getColor(R.color.home_text_color));
                txt_2500.setTextColor(getResources().getColor(R.color.white));
                txt_2000.setTextColor(getResources().getColor(R.color.home_text_color));
                txt_1500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
                txt_2000.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey));
                txt_2500.setBackground(getResources().getDrawable(R.drawable.rounded_corner_like));
                break;
            case R.id.promo_code:
                promoCodeDialog();
                break;
            case R.id.btn_add_money:
                startPayment();
                break;
        }
    }

    private void startPayment()
    {
        String money=edt_wallet.getValue();
        double dm=Double.parseDouble(money);
        createOrder((int) dm);
    }

    private void createOrder(final int amount)
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("amount=").append(amount*100);

        String content=stringBuilder.toString();

        ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.CREATE_ORDER, 0, content, true, "loading ...", new ApiResponse() {
            @Override
            public void getApiResponse(String responseData, int serviceCounter) {
                try{
                    JSONObject jsonObject=new JSONObject(responseData);
                    String id=jsonObject.getString("id");

                    double amt=amount*100;
                    startPayment(amt,id);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        apiConsumer.execute();
    }

    private void addMoney(String order_id)
    {
        if(edt_wallet.getValue().equalsIgnoreCase(""))
        {
            edt_wallet.setError("Please Enter Amount First");
            return;
        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("amount=").append(edt_wallet.getValue());
        stringBuilder.append("&order_id=").append(order_id);
        stringBuilder.append("&user_id=").append(AppPref.getInstance().getUSERID());

        String content = stringBuilder.toString();

        ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.UPDATE_WALLET, 1, content, true, "loading ...", new ApiResponse() {
            @Override
            public void getApiResponse(String responseData, int serviceCounter) {
                try {
                    Log.d("responseData", responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    if (status.equalsIgnoreCase("1")) {
                        edt_wallet.setValue("");
                    }
                    toastMessage(msg);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        apiConsumer.execute();
    }

    private void promoCodeDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inf = LayoutInflater.from(this);
        View v1 = inf.inflate(R.layout.dialog_promocode, null);

        ImageView back = (ImageView) v1.findViewById(R.id.back);

        final CustomEditText edt_promo_code = (CustomEditText) v1.findViewById(R.id.edt_promo_code);
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
                    edt_promo_code.setError("Please Enter Promocode");
                    return;
                }
            }
        });
    }

    public void startPayment(double amount,String order_id) {
        Checkout checkout = new Checkout();

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");

            options.put("description", "Reference No. #123456");
            options.put("currency", "INR");

            int amt=(int) amount;
            options.put("amount", ""+amt);
            options.put("order_id",order_id);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        addMoney(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
            toastMessage(s);
    }
}
