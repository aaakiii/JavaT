package com.example.aki.javaq.Presentation;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aki.javaq.Domain.Entity.PostMain;
import com.example.aki.javaq.Domain.Entity.User;
import com.example.aki.javaq.Domain.Helper.FirebaseNodes;
import com.example.aki.javaq.Domain.Helper.JavaQPreferences;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.example.aki.javaq.Domain.Helper.PictureUtils;
import com.example.aki.javaq.Presentation.Community.CommunityListActivity;
import com.example.aki.javaq.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MinaFujisawa on 2017/07/13.
 */

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private View mParentLayout;
    private CircleImageView mMyIconImageView;
    private TextView mEditIconTextView;
    private EditText mAddUserNameTextView;
    private TextView mErrorTextView;
    private MenuItem mSaveButton;
    private String mPicturePath;
    private String mPictureUri;
    private String mUserName;
    private int mIconViewWith;
    private int mIconViewHeight;
    private boolean mTappable;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private StorageReference mUserPicReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean isFromSignIn = false;
    //    public static final int DEFAULT_MSG_LENGTH_LIMIT = 20;
    public static final int RESULT_LOAD_IMAGE = 1;
    private final int REQUEST_PERMISSION_PHONE_STATE = 1;
    public static final String NEW_USER = "new_user";
    public static final String TAG = "tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_activity);

        Intent i = getIntent();
        isFromSignIn = i.getBooleanExtra(NEW_USER, false);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_with_button);
        setSupportActionBar(myToolbar);
        if (!isFromSignIn) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        mDatabaseReference = FirebaseLab.getFirebaseDatabaseReference();
        mUserPicReference = FirebaseLab.getStorageReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    mCurrentUser = FirebaseLab.getFirebaseUser();
                    setView();
                } else {
                    mAddUserNameTextView.setText("");
                }
            }
        };
        FirebaseLab.getFirebaseAuth().addAuthStateListener(mAuthListener);


        mMyIconImageView = (CircleImageView) findViewById(R.id.add_user_icon);
        updatePhotoView();
        mMyIconImageView.setOnClickListener(this);

        mEditIconTextView = (TextView) findViewById(R.id.add_icon_text);
        mEditIconTextView.setOnClickListener(this);

        mAddUserNameTextView = (EditText) findViewById(R.id.add_user_name);
        mAddUserNameTextView.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
//        InputFilter[] inputFilter = new InputFilter[1];
//        inputFilter[0] = new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT);
//        mAddUserNameTextView.setFilters(inputFilter);
        mAddUserNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                invalidateOptionsMenu();

                //Detect inputted user name
                if (0 < s.toString().trim().length() && s.toString().trim().length() <= 20) {
                    mTappable = true;
                } else {
                    mTappable = false;
                }

                //For error
                if (s.toString().length() > 20) {
                    mErrorTextView.setText(R.string.error_user_name);
                } else {
                    mErrorTextView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mErrorTextView = (TextView) findViewById(R.id.error_text);

        mParentLayout = getWindow().getDecorView().getRootView();
        mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIconViewWith = mMyIconImageView.getWidth();
                mIconViewHeight = mMyIconImageView.getHeight();
                removeOnGlobalLayoutListener(mParentLayout.getViewTreeObserver(), mGlobalLayoutListener);
            }
        };
        mParentLayout.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);

    }

    private void setView() {
        mDatabaseReference.child(FirebaseNodes.User.USER_CHILD)
                .child(mCurrentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                //Display User name
                mUserName = snapshot.child(FirebaseNodes.User.USER_NAME).getValue().toString();
                mAddUserNameTextView.setText(mUserName);

                //Display User picture
                StorageReference rootRef = FirebaseLab.getStorageReference().child(FirebaseNodes.UserPicture.USER_PIC_CHILD);
                rootRef.child(mCurrentUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //If there's a picture in the storage, set the picture
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(mMyIconImageView);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            observer.removeGlobalOnLayoutListener(listener);
        } else {
            observer.removeOnGlobalLayoutListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
        showPermission();
    }

    private void showPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "Rationale", android.Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_PHONE_STATE);
            } else {
                requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_PHONE_STATE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            updatePhotoView();
            cursor.close();
        }
    }

    private void updatePhotoView() {
        if (mPicturePath == null) {
            mMyIconImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_user_default));
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPicturePath);
            mMyIconImageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_save:

                //TODO: たまに取得できなくてエラーになる…
                mCurrentUser = FirebaseLab.getFirebaseUser();

                mUserName = mAddUserNameTextView.getText().toString();

                //Save name to the database
                User user = new User(mUserName, mCurrentUser.getUid());
                mDatabaseReference.child(FirebaseNodes.User.USER_CHILD)
                        .child(mCurrentUser.getUid()).setValue(user);

                //Save picture to the storage if only user set the local image
                if (mPicturePath != null) {
                    Uri file = Uri.fromFile(new File(mPicturePath));
                    StorageReference picRef = mUserPicReference.child(FirebaseNodes.UserPicture.USER_PIC_CHILD)
                            .child(mCurrentUser.getUid());
                    UploadTask uploadTask = picRef.putFile(file);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
                }


                //TODO:理想はコメントから来た人はdetailページからコメント入力画面に遷移
                Intent intent = new Intent(this, CommunityListActivity.class);
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

        mSaveButton = menu.findItem(R.id.action_save);
        mSaveButton.setEnabled(false);

        if (mTappable) {
            mSaveButton.setEnabled(true);
        } else {
            mSaveButton.setEnabled(false);
        }
        return true;
    }


//    private boolean hasSpecialSymbol(String input) {
//        String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
//        Pattern p1 = Pattern.compile("[^a-z] | [\uD83C-\uDBFF\uDC00-\uDFFF]+", Pattern.CASE_INSENSITIVE);
//        Pattern p2 = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
//        boolean b = p1.matcher(input).matches() || p2.matcher(input).matches();
//
//        return (b) ? true : false;
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }


}
