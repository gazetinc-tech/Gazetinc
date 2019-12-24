package com.gazetinc.shopninja.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gazetinc.shopninja.R;


/**
 * Created by TalkCharge on 06-09-2017.
 */

public abstract class BaseFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initializeView(view);
        return view;
    }

    public abstract int getFragmentLayout();

    public abstract void initializeView(View view);

    public void sendToActivity(Class className) {
        Intent intent = new Intent(getActivity(), className);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }


    public void sendToActivity(Class className, int flags) {
        Intent intent = new Intent(getActivity(), className);
        intent.setFlags(flags);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    public void sendToActivity(Class className, String[] params) {
        Intent intent = new Intent(getActivity(), className);
        for (byte i = 0; i < params.length; i++) {
            String s = params[i];
            String a[] = s.split(";");
            intent.putExtra(a[0], a[1]);
        }
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    public void setFragment(Fragment fragment, int id) {
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(id, fragment);
        t.commit();
    }

    public void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public void alertMessage(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();
    }
}
