package com.gazetinc.shopninja.Activity;

import android.os.Bundle;

import com.gazetinc.shopninja.R;

public class TransactionActivity extends BaseActivity {
    @Override
    public void initialize(Bundle save) {
        setTitle("Transactions");
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_transactions;
    }
}
