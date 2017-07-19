package com.example.aki.javaq.Presentation.Community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aki.javaq.Domain.Entity.PostMainContents;
import com.example.aki.javaq.Domain.Helper.JavaQPreferences;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.aki.javaq.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CommunityPostActivity extends AppCompatActivity {

    public static final String MESSAGES_CHILD = "messages";
    private static final String TAG = "CommunityPostActivity";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private SharedPreferences mSharedPreferences;
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 30;
    public static String mUsername;
    private static final String MESSAGE_URL = "http://javaq.firebase.google.com/";
    private Button mPostButton;
    private EditText mEditText;
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseUser mFirebaseUser;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAnalytics mFirebaseAnalytics;
    public DatabaseReference mFirebaseDatabaseReference;
    private static String mPhotoUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_post_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //New Child entries
        mFirebaseDatabaseReference = FirebaseLab.getFirebaseDatabaseReference();

        mPostButton = (Button) findViewById(R.id.post_button);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostMainContents postMainContents = new PostMainContents(mEditText.getText().toString(), mUsername, false, mPhotoUrl);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD).push().setValue(postMainContents);
                mEditText.setText("");
                mFirebaseAnalytics.logEvent(MESSAGE_SENT_EVENT, null);

                Intent intent = new Intent(CommunityPostActivity.this, CommunityListActivity.class);
                startActivity(intent);

            }
        });

        mFirebaseAnalytics = FirebaseLab.getFirebaseAnalytics(this);
        mFirebaseRemoteConfig = FirebaseLab.getFirebaseRemoteConfig();
        FirebaseLab.SetConfig();
        FirebaseLab.fetchConfig();

        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        mFirebaseUser = FirebaseLab.getFirebaseUser();
        mUsername = mFirebaseUser.getDisplayName();

        if(mFirebaseUser.getPhotoUrl() != null){
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        }

        mEditText = (EditText) findViewById(R.id.edit_post);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences.getInt(JavaQPreferences.FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mPostButton.setEnabled(true);
                } else {
                    mPostButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



//    public static Action getMessageViewAction(PostMainContents postMainContents){
//        return new Action.Builder(Action.Builder.VIEW_ACTION)
//                .setObject(postMainContents.getName(), MESSAGE_URL.concat(postMainContents.getId()))
//                .setMetadata(new Action.Metadata.Builder().setUpload(false))
//                .build();
//    }
//
//    public static Indexable getMessageIndexable(PostMainContents postMainContents){
//
//        PersonBuilder sender = Indexables.personBuilder()
//                .setIsSelf(mUsername.equals(postMainContents.getName()))
//                .setName(postMainContents.getName())
//                .setUrl(MESSAGE_URL.concat(postMainContents.getId() + "/sender"));
//
//        PersonBuilder recipient = Indexables.personBuilder()
//                .setName(mUsername)
//                .setUrl(MESSAGE_URL.concat(postMainContents.getId() + "/recipient"));
//
//        Indexable messageToIndex = Indexables.messageBuilder()
//                .setName(postMainContents.getText())
//                .setUrl(MESSAGE_URL.concat(postMainContents.getId()))
//                .setSender(sender)
//                .setRecipient(recipient)
//                .build();
//
//
//        return messageToIndex;
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);


        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    Log.d(TAG, "Uri: " + uri.toString());

                    PostMainContents tempMessage = new PostMainContents(null, mUsername, false, LOADING_IMAGE_URL);
                    mFirebaseDatabaseReference.child(MESSAGES_CHILD).push()
                            .setValue(tempMessage, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError,
                                                       DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        String key = databaseReference.getKey();
                                        StorageReference storageReference =
                                                FirebaseStorage.getInstance()
                                                        .getReference(mFirebaseUser.getUid())
                                                        .child(key)
                                                        .child(uri.getLastPathSegment());

                                        putImageInStorage(storageReference, uri, key);
                                    } else {
                                        Log.w(TAG, "Unable to write message to database.",
                                                databaseError.toException());
                                    }
                                }
                            });
                }
            }
        }else if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Use Firebase Measurement to log that invitation was sent.
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_sent");

                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                // Use Firebase Measurement to log that invitation was not sent
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_not_sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, payload);

                // Sending failed or it was canceled, show failure message to the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }

    }
    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener(CommunityPostActivity.this,
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
//                            PostMainContents postMainContents = new PostMainContents(null, mUsername, true, task.getResult().getMetadata().getDownloadUrl().toString());
//                            mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(key).setValue(postMainContents);
                        } else {
                            Log.w(TAG, "Image upload task was not successful.", task.getException());
                        }
                    }
                });
    }

}
