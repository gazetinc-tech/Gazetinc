package com.gazetinc.shopninja.Activity;

import android.os.Bundle;

import com.gazetinc.shopninja.R;

public class TermsandConditionsActivity extends BaseActivity {

    @Override
    public void initialize(Bundle save) {
        setTitle("Terms & Conditions");
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_terms_conditions;
    }
}
