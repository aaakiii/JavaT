package com.example.aki.javaq.Domain.Entity;

import android.net.Uri;

import java.net.URI;
import java.net.URL;
import java.util.UUID;

/**
 * Created by AKI on 2017-07-11.
 */

public class User {
//    private UUID mUserId;
    private String mUserName;
    private Uri mUserIcon;

    public User() {
    }

    public User(String mUserName, Uri mUserIcon) {
        this.mUserName = mUserName;
        this.mUserIcon = mUserIcon;
//        this.mUserId = mUserId;
    }
//
//    public UUID getmUserId() {
//        return mUserId;
//    }
//
//    public void setmUserId(UUID mUserId) {
//        this.mUserId = mUserId;
//    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

//    public String getmUserIcon() {
//        return "IMG_" + getmUserId().toString() + ".jpg";
//    }

//    public void setmUserIcon(URL mUserIcon) {
//        this.mUserIcon = mUserIcon;
//    }

}
