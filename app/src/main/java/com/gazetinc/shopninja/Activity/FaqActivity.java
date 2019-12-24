package com.gazetinc.shopninja.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;


import com.gazetinc.shopninja.Adapter.CustomExpandableListAdapter;
import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FaqActivity extends BaseActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

    @Override
    public void initialize(Bundle save) {
        setTitle("FAQ");
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        ApiConsumer apiConsumer = new ApiConsumer(this, AppUrl.FAQ_URL, 0, "", true, "loading ...", new ApiResponse() {
            @Override
            public void getApiResponse(String responseData, int serviceCounter) {

                try {
                    Log.d("responseData", responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String question = jsonObject.getString("question");
                        String answer = jsonObject.getString("answer");
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(answer);
                        expandableListDetail.put(question, arrayList);

                    }
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(FaqActivity.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        apiConsumer.execute();






        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            int previousGroup = 1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_faq;
    }

}
