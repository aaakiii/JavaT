package com.example.aki.javaq.Community;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aki.javaq.PictureUtils;
import com.example.aki.javaq.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MinaFujisawa on 2017/07/13.
 */

public class CommunityAddCommentActivity extends AppCompatActivity {

    private EditText mCommentEditTextView;
    private String mCommentText;
    private MenuItem mPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_post_activity);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_with_button);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //TODO: drawableにclose iconいれてそれにセットする
        ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);


        mCommentEditTextView = (EditText) findViewById(R.id.edit_post);
        mCommentEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        mCommentEditTextView.setHint(R.string.add_comment_ph);
        //show keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mCommentEditTextView, InputMethodManager.SHOW_IMPLICIT);
//        if (!(imm == null))
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);


        mCommentEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCommentText = s.toString();
                invalidateOptionsMenu();
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        mPostButton = menu.findItem(R.id.action_post);
        mPostButton.setEnabled(false);

        if (mCommentText == null) {
            mPostButton.setEnabled(false);
        } else {
            if (mCommentText.equals("")) {
                mPostButton.setEnabled(false);
            } else {
                mPostButton.setEnabled(true);

            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_post:
                //TODO: mCommentTextをデータベースにセット
                Toast.makeText(this, "enable", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }


}
