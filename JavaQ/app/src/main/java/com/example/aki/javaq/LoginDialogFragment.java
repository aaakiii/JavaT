package com.example.aki.javaq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by MinaFujisawa on 2017/06/13.
 */

// TODO: ログインボタン
public class LoginDialogFragment extends DialogFragment {
    private TextView mLaterTextView;

    public static LoginDialogFragment newInstance(Fragment target, int requestCode) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.setTargetFragment(target,requestCode);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.google_sign_in_activity, null);
        mLaterTextView = (TextView) view.findViewById(R.id.close_dialog);
        mLaterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

}
