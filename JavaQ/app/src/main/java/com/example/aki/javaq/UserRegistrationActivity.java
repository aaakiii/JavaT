package com.example.aki.javaq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

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
    private File mPhotoFile;
    private String mPicturePath;
    private String mUserName;
    private int mIconViewWith;
    private int mIconViewHeight;
    private boolean mTappable;

    public static final int RESULT_LOAD_IMAGE = 1;
    private final int REQUEST_PERMISSION_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_with_button);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        mMyIconImageView = (CircleImageView) findViewById(R.id.add_user_icon);

        //TODO:Googleから取得したプロフィール画像をセット
//        mPicturePath = google
        updatePhotoView();
        mMyIconImageView.setOnClickListener(this);

        mEditIconTextView = (TextView) findViewById(R.id.add_icon_text);
        mEditIconTextView.setOnClickListener(this);

        //TODO:ユーザー名データベースから取得（編集時用）
        mUserName = "Taro"; //ダミー

        mAddUserNameTextView = (EditText) findViewById(R.id.add_user_name);
        mAddUserNameTextView.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        //For people who already set name
        if (mUserName != null) {
            mAddUserNameTextView.setText(mUserName);
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
                if (s.toString() == null) {
                    mTappable = false;
                } else {
                    if (s.toString().length() < 2 || hasSpecialSymbol(s.toString())) {
                        mTappable = false;
                    } else if (mUserName.equals(s.toString())) {
                        mTappable = false;
                    } else {
                        mTappable = true;
                        mUserName = s.toString();
                    }
                }


                //For error
                if (hasSpecialSymbol(mUserName)) {
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
                //TODO:mUserNameとmPicturePathをデータベスにset
                //Todo:"保存しました!"みたいなトースト投げる
                Toast.makeText(this, "success!", Toast.LENGTH_SHORT).show();
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
