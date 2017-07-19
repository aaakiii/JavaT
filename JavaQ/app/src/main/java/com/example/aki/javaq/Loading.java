package com.example.aki.javaq;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MinaFujisawa on 2017/07/18.
 */

public class Loading extends AppCompatActivity{
    private android.app.ProgressDialog progress;
    private Context mContext;

    public Loading(Context context) {
        this.mContext = context;
    }

    public void showLoadingDialog() {
        if (progress == null) {
            progress = new android.app.ProgressDialog(mContext);
//            progress.setTitle(getString(R.string.loading_title));
//            progress.setTitle("Loading");
//            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.setContentView(R.layout.progressdialog);
            progress.show();
        }
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

}
