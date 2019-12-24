package com.gazetinc.shopninja.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gazetinc.shopninja.Fragment.HomeFragment;
import com.gazetinc.shopninja.Fragment.ProfileFragment;
import com.gazetinc.shopninja.Fragment.WalletFragment;
import com.gazetinc.shopninja.Fragment.WalletTransactionFragment;
import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.AppPref;
import com.gazetinc.shopninja.Utility.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setTitle("Home");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view=(View) navigationView.getHeaderView(0);

        TextView name=(TextView) view.findViewById(R.id.name);
        TextView email=(TextView) view.findViewById(R.id.email);

        name.setText(AppPref.getInstance().getNAME());
        email.setText(AppPref.getInstance().getEMAIL());

        try {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_home);
            BottomNavigationViewHelper.disableShiftMode(navigation);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        setFragment(new HomeFragment(),R.id.frame);

    }

    public void setFragment(Fragment fragment, int id) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(id, fragment);
        t.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(new HomeFragment(),R.id.frame);
                    return true;
                case R.id.navigation_wallet:
                    setFragment(new WalletTransactionFragment(),R.id.frame);
                    return true;
                case R.id.navigation_profile:
                    setFragment(new ProfileFragment(),R.id.frame);
                    return true;


            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_faq:
                sendToActivity(FaqActivity.class);
                break;
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_home:
                setFragment(new HomeFragment(),R.id.frame);
                break;
            case R.id.nav_wallet:
                sendToActivity(PaymentDemo.class);
                break;
            case R.id.nav_share:
                sendToActivity(ShareActivity.class);
                break;
            case R.id.nav_terms:
                sendToActivity(TermsandConditionsActivity.class);
                break;
            case R.id.nav_premium:
                sendToActivity(ConvertToPremium.class);
                break;
            case R.id.nav_transactions:
                sendToActivity(TransactionActivity.class);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Logout");
        adb.setMessage("Are you sure you want to Logout?");
        adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppPref.getInstance().setUSERID("");
                AppPref.getInstance().setNAME("");
                AppPref.getInstance().setEMAIL("");
                AppPref.getInstance().setMOBILE("");
                AppPref.getInstance().setVERIFIED("");
                sendToActivity(LoginActivity.class,Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
        });
        adb.setNegativeButton("NO", null);
        adb.show();
    }

    private void sendToActivity(Class className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    public void sendToActivity(Class className, int flags) {
        Intent intent = new Intent(this, className);
        intent.setFlags(flags);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }
}
