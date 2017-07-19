package com.example.aki.javaq.Domain.Usecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.aki.javaq.Presentation.Community.CommunityPostActivity;
import com.example.aki.javaq.Presentation.Community.LoginDialogFragment;
import com.example.aki.javaq.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

public class SignInLab {

    private static final String TAG = "SignInActivity";
    private static FirebaseAuth mFirebaseAuth;

    public SignInLab(){

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

    public static void signOut(){
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
