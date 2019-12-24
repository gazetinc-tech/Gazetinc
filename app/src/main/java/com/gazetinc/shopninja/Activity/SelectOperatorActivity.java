package com.gazetinc.shopninja.Activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.gazetinc.shopninja.Adapter.SingleAdapter;
import com.gazetinc.shopninja.Model.OperatorModel;
import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectOperatorActivity extends BaseActivity implements ApiResponse, SingleAdapter.ReturnView
{
    private ListView listview;

    private ArrayList<OperatorModel> operatorModelArrayList=new ArrayList();

    private String type="";

    @Override
    public void initialize(Bundle save) {
        setTitle("Select Operator");
        listview=(ListView) findViewById(R.id.listview);
        save=getIntent().getExtras();
        if(save!=null)
        {
            type=save.getString("type");
        }
        dummydata();
    }

    private void dummydata()
    {
        ApiConsumer apiConsumer=new ApiConsumer(this, AppUrl.OPERATOR_LIST,0,"",true,"loading ...",this);
        apiConsumer.execute();
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_select_operator;
    }

    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try
        {
            Log.d("responsedata",responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObject=jsonArray.getJSONObject(i);
                String operator_name=jsonObject.getString("operator_name");
                String operator_type=jsonObject.getString("operator_type");
                String operator_code=jsonObject.getString("operator_code");

                if(type.equalsIgnoreCase("recharge") && operator_type.equalsIgnoreCase("Prepaid"))
                {
                    operatorModelArrayList.add(new OperatorModel(operator_name,operator_type,operator_code));
                }
                else if(type.equalsIgnoreCase("dth") && operator_type.equalsIgnoreCase("DTH"))
                {
                    operatorModelArrayList.add(new OperatorModel(operator_name,operator_type,operator_code));
                }


            }

            listview.setAdapter(new SingleAdapter(this,android.R.layout.simple_list_item_1,operatorModelArrayList,this,0));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void getAdapterView(View view, List objects, int position, int from) {
        TextView text1=(TextView) view.findViewById(android.R.id.text1);

        final OperatorModel operatorModel=operatorModelArrayList.get(position);

        text1.setText(operatorModel.getOperator_name());

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equalsIgnoreCase("recharge"))
                {
                    RechargeActivity.operatorModel=operatorModel;
                    finish();
                }
                else if(type.equalsIgnoreCase("dth"))
                {
                    //DTHActivity.dthoperatormodel=operatorModel;
                    finish();
                }
            }
        });


    }
}
