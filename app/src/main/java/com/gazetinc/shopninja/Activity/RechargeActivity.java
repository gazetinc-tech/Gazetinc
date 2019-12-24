package com.gazetinc.shopninja.Activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gazetinc.shopninja.Model.OperatorModel;
import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppPref;
import com.gazetinc.shopninja.Utility.AppUrl;
import com.gazetinc.shopninja.Utility.CustomEditText;
import org.json.JSONObject;

public class RechargeActivity extends BaseActivity implements View.OnClickListener, ApiResponse {
    private CustomEditText edt_amount;
    private CustomEditText edt_select_operator;
    private AutoCompleteTextView edt_mobile_number;
    
    private RelativeLayout relative_custom;
    private Button btn_recharge;
    private RadioGroup radio_group,top_special;
    private TextView error;
    public static OperatorModel operatorModel;

    @Override
    public void initialize(Bundle save) {
        operatorModel=null;
        edt_select_operator = (CustomEditText) findViewById(R.id.edt_select_operator);
        edt_mobile_number = (AutoCompleteTextView) findViewById(R.id.auto_value);
        relative_custom = (RelativeLayout) findViewById(R.id.relative_custom);
        edt_amount = (CustomEditText) findViewById(R.id.edt_amount);
        btn_recharge = (Button) findViewById(R.id.btn_recharge);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        error = (TextView) findViewById(R.id.error);
        


        edt_mobile_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});



        edt_mobile_number.setThreshold(4);

      

        ImageView image_operator = (ImageView) edt_select_operator.findViewById(R.id.drawableleft);
        image_operator.setImageDrawable(getResources().getDrawable(R.mipmap.ic_operator));

//        ImageView image_opeartor_right = (ImageView) edt_select_operator.findViewById(R.id.drawableright);
//        image_opeartor_right.setImageDrawable(getResources().getDrawable(R.mipmap.leftarrow));

        TextView text=(TextView)  edt_select_operator.findViewById(R.id.browseplans);
        text.setText("SEARCH");
        text.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        text.setTextColor(getResources().getColor(R.color.white));
        text.setPadding(10,10,10,10);


        ImageView image_amount = (ImageView) edt_amount.findViewById(R.id.drawableleft);
        image_amount.setImageDrawable(getResources().getDrawable(R.mipmap.ic_amount));


        ImageView drawableright = (ImageView) findViewById(R.id.drawableright);
        drawableright.setImageDrawable(getResources().getDrawable(R.mipmap.ic_contact));


        EditText edit_operator = (EditText) edt_select_operator.findViewById(R.id.value);
        edit_operator.setEnabled(false);
        edit_operator.setClickable(true);


        edt_select_operator.setOnClickListener(this);
        btn_recharge.setOnClickListener(this);

      
        final RadioButton prepaid = (RadioButton) radio_group.findViewById(R.id.prepaid);
        final RadioButton postpaid = (RadioButton) radio_group.findViewById(R.id.postpaid);

      
        prepaid.setChecked(true);
        edt_amount.setFilters(4);
     //   edt_mobile_number.addTextChangedListener(watch);

        edt_select_operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToActivity(SelectOperatorActivity.class,new String[]{"type;recharge"});
            }
        });
        
    }

    public void onResume()
    {
        super.onResume();
        if(operatorModel!=null)
        {
            edt_select_operator.setValue(operatorModel.getOperator_name());
        }
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_recharge:
                recharge();
                break;
        }
    }

    private void recharge()
    {


        String str_number=edt_mobile_number.getText().toString();
        String str_amount=edt_amount.getValue();

        if(operatorModel==null)
        {
            toastMessage("Please Select Operator");
            return;
        }
        if(str_number.equalsIgnoreCase(""))
        {
            toastMessage("Please Enter Mobile Number");
            return;
        }
        else if(str_amount.equalsIgnoreCase(""))
        {
            toastMessage("Please Enter Amount");
            return;
        }

        String str_operator=operatorModel.getOperator_code();



            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("number=").append(str_number);
            stringBuilder.append("&amount=").append(str_amount);
            stringBuilder.append("&operator=").append(str_operator);
            stringBuilder.append("&operatorname=").append(edt_select_operator.getValue());
            stringBuilder.append("&userid=").append(AppPref.getInstance().getUSERID());

            String content = stringBuilder.toString();

            ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.RECHARGE_API, 0, content, true, "loading ...", this);
            apiConsumer.execute();

    }

    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try
        {
            Log.d("responsedata",responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            String status_msg=jsonObject.getString("status_msg");
            toastMessage(status_msg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
