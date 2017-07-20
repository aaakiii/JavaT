package com.example.aki.javaq.Presentation.Community;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.example.aki.javaq.Domain.Usecase.SignInLab;
import com.example.aki.javaq.Presentation.UserRegistrationActivity;
import com.example.aki.javaq.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by MinaFujisawa on 2017/06/13.
 */

// TODO: ログインボタン
public class LoginDialogFragment extends DialogFragment {
    private TextView mLaterTextView;
    //Fragment target, int requestCode

    private static final int REQUEST_CODE_LOGIN = 1;

    private static final String TAG = "SignInActivity";
    private static FirebaseAuth mFirebaseAuth;
    private SignInButton mSignInButton;
    private SignInLab mSignInLab;


    public static LoginDialogFragment newInstance(Fragment target, int requestCode) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.setTargetFragment(fragment, REQUEST_CODE_LOGIN);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.google_sign_in_activity, null);

        mSignInLab = new SignInLab(getActivity(), getActivity(), getActivity());

        mSignInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(mSignInLab.getSignInIntent(), SignInLab.RC_SIGN_IN);
            }
        });

        mLaterTextView = (TextView) view.findViewById(R.id.close_dialog);
        mLaterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSignInLab.disconnect();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mSignInLab.isAuthenticateSuccess(requestCode, data)){
            Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();

            //TODO: 新規かどうか取得
            boolean isNewUser = true;
            if(isNewUser){
                Intent intent = new Intent(getContext(), UserRegistrationActivity.class);
                startActivity(intent);
            } else {
                dismiss();
            }
        }

    }


}
