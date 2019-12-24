package com.gazetinc.shopninja.Activity;

import android.os.Bundle;

import com.gazetinc.shopninja.R;

public class ConvertToPremium extends BaseActivity {
    @Override
    public void initialize(Bundle save) {
        setTitle("Convert To Premium");
    }

    @Override
    public int getActivityLayout() {
        return R.layout.convert_to_premium;
    }
}
