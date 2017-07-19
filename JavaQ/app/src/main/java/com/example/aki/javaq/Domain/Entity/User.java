package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-11.
 */

public class User {
    private String mUserId;
    private String mUserName;
    private String mUserIcon;


    public User(String mUserId, String mUserName, String mUserIcon) {
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mUserIcon = mUserIcon;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserIcon() {
        return "IMG_" + getmUserId().toString() + ".jpg";
    }

    public void setmUserIcon(String mUserIcon) {
        this.mUserIcon = mUserIcon;
    }

}
