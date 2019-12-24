package com.gazetinc.shopninja.Fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gazetinc.shopninja.Activity.RechargeActivity;
import com.gazetinc.shopninja.Model.CityModel;
import com.gazetinc.shopninja.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends BaseFragment implements View.OnClickListener
{
    private LinearLayout cities;

    private ArrayList<CityModel> cityModelArrayList=new ArrayList<>();

    private ImageView refer;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initializeView(View view) {
        getActivity().setTitle("Home");

        cities = (LinearLayout) view.findViewById(R.id.cities);
        refer=(ImageView) view.findViewById(R.id.refer);

        cities.removeAllViews();



        add();


        for (int i = 0; i < cityModelArrayList.size(); i++) {
            final CityModel hotelModel = cityModelArrayList.get(i);
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.layout_cities, null);
            ImageView image1 = (ImageView) view1.findViewById(R.id.city_image);
            final TextView title = (TextView) view1.findViewById(R.id.city_title);
            image1.setImageResource(hotelModel.getUrl());
            title.setText(hotelModel.getName());
            final int j = i;
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(title.getText().toString().equalsIgnoreCase("Recharge"))
                    {
                        sendToActivity(RechargeActivity.class);
                    }
                    else
                        toastMessage("Coming Soon");
                }
            });
            cities.addView(view1);
        }

        refer.setOnClickListener(this);
    }

    private void add()
    {
        cityModelArrayList.add(new CityModel("Recharge",R.drawable.trn_recharge));
        cityModelArrayList.add(new CityModel("DataCard",R.drawable.trn_datacard));
        cityModelArrayList.add(new CityModel("Electricity",R.drawable.trn_electricity));
        cityModelArrayList.add(new CityModel("Landline",R.drawable.trn_landline));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.refer:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Shopninja");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Share Shopninja and Earn Lots of Cashback");
                startActivity(Intent.createChooser(sharingIntent, "share"));
                break;
        }
    }
}
