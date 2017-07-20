package com.example.aki.javaq.Domain.Usecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.aki.javaq.Presentation.Community.CommunityPostActivity;
import com.example.aki.javaq.Presentation.Community.LoginDialogFragment;
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
 * Created by AKI on 2017-07-19.
 */

public class SignInLab extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    public static final int RC_SIGN_IN = 9001;

    private static FirebaseAuth mFirebaseAuth;
    private static GoogleApiClient mGoogleApiClient;
    private Activity mActivity;
    private Context mContext;
    private FragmentActivity mFragmentActivity;
    private LoginDialogFragment mLoginDialogFragment;
    android.content.res.Resources res;


    public SignInLab(Activity mActivity, Context mContext, FragmentActivity mFragmentActivity) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mFragmentActivity = mFragmentActivity;
        res = mActivity.getResources();
    }

    public static void handleFirebaseAuthResult(AuthResult authResult, Activity activity) {
        if (authResult != null) {
            // Welcome the user
            FirebaseUser user = authResult.getUser();
            Toast.makeText(activity, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

            // Go back to the main activity
            activity.startActivity(new Intent(activity, CommunityPostActivity.class));
        }
    }

    public Intent getSignInIntent() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(res.getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(mFragmentActivity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        return signInIntent;
    }

    public void disconnect() {
        mGoogleApiClient.stopAutoManage(new CommunityPostActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(mContext, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    public boolean isAuthenticateSuccess(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                SignInLab.firebaseAuthWithGoogle(account, mActivity);
                return true;
            } else {
                // Google Sign-In failed
                Log.e(TAG, "Google Sign-In failed.");
                return false;
            }
        } return false;
    }


    public static void signOut() {
        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        mFirebaseAuth.signOut();
        //TODO: Google sign-outの検討
        // Google sign out
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }

    public static void firebaseAuthWithGoogle(GoogleSignInAccount acct, final Activity activity) {
        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            activity.startActivity(new Intent(activity, CommunityPostActivity.class));
                            activity.finish();

                        }
                    }
                });
    }

}
