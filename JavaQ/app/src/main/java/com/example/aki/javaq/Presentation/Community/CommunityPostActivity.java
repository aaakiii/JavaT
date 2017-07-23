package com.example.aki.javaq.Presentation.Community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.aki.javaq.Domain.Entity.PostMain;
import com.example.aki.javaq.Domain.Helper.FirebaseNodes;
import com.example.aki.javaq.Domain.Helper.JavaQPreferences;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.example.aki.javaq.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Created by MinaFujisawa on 2017/07/13.
 */

public class CommunityPostActivity extends AppCompatActivity {

    private MenuItem mSaveButton;
    private boolean mTappable;
    private SharedPreferences mSharedPreferences;
    private EditText mEditTextView;
    public static String mUsername;
    private String mPostBody;
    private String mUserId;

    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseUser mFirebaseUser;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAnalytics mFirebaseAnalytics;
    public DatabaseReference mFirebaseDatabaseReference;
    private static String mPhotoUrl;

    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 30;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    private static final String TAG = "CommunityPostActivity";
    private static final String POST_SENT_EVENT = "post_sent";
    private long mPostTime;
    private int mCommentNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_post_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_with_button);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.ic_close);


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //New Child entries
        mFirebaseDatabaseReference = FirebaseLab.getFirebaseDatabaseReference();
        mFirebaseAnalytics = FirebaseLab.getFirebaseAnalytics(this);
        mFirebaseRemoteConfig = FirebaseLab.getFirebaseRemoteConfig();
        FirebaseLab.SetConfig();
        FirebaseLab.fetchConfig();

        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        mFirebaseUser = FirebaseLab.getFirebaseUser();
        mUsername = mFirebaseUser.getDisplayName();
        if (mFirebaseUser.getPhotoUrl() != null) {
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        }


        mEditTextView = (EditText) findViewById(R.id.edit_post);
        mEditTextView.setFilters(new InputFilter[]{new InputFilter
                .LengthFilter(mSharedPreferences
                .getInt(JavaQPreferences
                        .FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});
        mEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                invalidateOptionsMenu();

                if (s.toString().trim().length() > 0) {
                    mTappable = true;
                } else {
                    mTappable = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_post:
                mPostBody = mEditTextView.getText().toString();
                mUserId = mFirebaseUser.getUid();
                mPostTime = System.currentTimeMillis();

                //Save post to the Firebase
                DatabaseReference ref = mFirebaseDatabaseReference.child(FirebaseNodes.PostMain.POSTS_CHILD);
                String key = ref.push().getKey();
                PostMain post = new PostMain(key, mUserId, mPostTime, mPostBody, null);
                ref.child(key).setValue(post);
                mFirebaseAnalytics.logEvent(POST_SENT_EVENT, null);

                Intent intent = new Intent(CommunityPostActivity.this, CommunityListActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        mSaveButton = menu.findItem(R.id.action_post);
        mSaveButton.setEnabled(false);

        if (mTappable) {
            mSaveButton.setEnabled(true);
        } else {
            mSaveButton.setEnabled(false);
        }
        return true;
    }

}
