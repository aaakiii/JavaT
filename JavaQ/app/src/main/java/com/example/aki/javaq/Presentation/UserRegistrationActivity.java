package com.example.aki.javaq.Presentation;

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
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aki.javaq.Domain.Entity.User;
import com.example.aki.javaq.Domain.Helper.FirebaseNodes;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.example.aki.javaq.Domain.Helper.PictureUtils;
import com.example.aki.javaq.Domain.Usecase.UserLab;
import com.example.aki.javaq.Presentation.Community.CommunityListActivity;
import com.example.aki.javaq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
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
    private UserLab mUserLab;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    public DatabaseReference mFirebaseDatabaseReference;
    private boolean isFromSignIn = false;

    public static final int RESULT_LOAD_IMAGE = 1;
    private final int REQUEST_PERMISSION_PHONE_STATE = 1;
    public static final String NEW_USER = "new_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_activity);

        Intent i = getIntent();
        isFromSignIn = i.getBooleanExtra(NEW_USER, false);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_with_button);
        setSupportActionBar(myToolbar);
        if (!isFromSignIn) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        mUserLab = new UserLab();
        mFirebaseUser = FirebaseLab.getFirebaseUser();
        mFirebaseDatabaseReference = FirebaseLab.getFirebaseDatabaseReference();


        mMyIconImageView = (CircleImageView) findViewById(R.id.add_user_icon);
        updatePhotoView();
        mMyIconImageView.setOnClickListener(this);

        mEditIconTextView = (TextView) findViewById(R.id.add_icon_text);
        mEditIconTextView.setOnClickListener(this);


        mAddUserNameTextView = (EditText) findViewById(R.id.add_user_name);
        mAddUserNameTextView.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        if (!isFromSignIn) {
            mFirebaseAuth = FirebaseLab.getFirebaseAuth();
            mFirebaseDatabaseReference.child(FirebaseNodes.User.USER_CHILD)
                    .child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    mUserName = snapshot.child(FirebaseNodes.User.USER_NAME).getValue().toString();
                    mAddUserNameTextView.setText(mUserName);
                        mPictureUri = snapshot.child(FirebaseNodes.User.USER_PIC_URI).getValue().toString();

                    //TODO:画像がないときはデフォルト画像をセット
                        Glide.with(getApplicationContext())
                                .load(Uri.parse(mPictureUri))
                                .into(mMyIconImageView);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

//            mFirebaseAuth.getCurrentUser()
//                    .reload()
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//                            mUserName = mFirebaseUser.getDisplayName();
//                            mAddUserNameTextView.setText(mUserName);
//                            Glide.with(getApplicationContext())
//                                    .load(mFirebaseUser.getPhotoUrl())
//                                    .into(mMyIconImageView);
//                        }
            });
        } else {
            //TODO:セットできない…。userがnullになるのは読み込みが遅いから？
            mFirebaseAuth = FirebaseLab.getFirebaseAuth();
            mFirebaseUser = FirebaseLab.getFirebaseUser();
            if (mFirebaseUser != null) {
                Glide.with(getApplicationContext())
                        .load(mFirebaseUser.getPhotoUrl())
                        .into(mMyIconImageView);
            }
        }


        mAddUserNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                invalidateOptionsMenu();

                //Detect inputted user name
                if (s.toString().trim().length() > 0 || hasSpecialSymbol(s.toString())) {
                    mTappable = true;
                } else {
                    mTappable = false;
                }

                //For error
                if (hasSpecialSymbol(s.toString().trim())) {
                    mErrorTextView.setText(R.string.error_user_name);
                } else {
                    mErrorTextView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
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
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPicturePath, mIconViewWith, mIconViewHeight);
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
        switch (item.getItemId()) {
            case R.id.action_save:

                if (mPicturePath != null) {
                    mPictureUri = Uri.fromFile(new File(mPicturePath)).toString();
                }
                mUserName = mAddUserNameTextView.getText().toString();


                User user = new User(mUserName, mPictureUri);
                mFirebaseDatabaseReference.child(FirebaseNodes.User.USER_CHILD)
                        .child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user);

//                mUserLab.updateProfile(mUserName, mPictureUri);


                //Todo:読み込み終わったらLoading非表示
//                new Loading(this).showLoadingDialog();

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


    private boolean hasSpecialSymbol(String input) {
        String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
        Pattern p1 = Pattern.compile("[^a-z] | [\uD83C-\uDBFF\uDC00-\uDFFF]+", Pattern.CASE_INSENSITIVE);
        Pattern p2 = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        boolean b = p1.matcher(input).matches() || p2.matcher(input).matches();

        return (b) ? true : false;
    }


}
