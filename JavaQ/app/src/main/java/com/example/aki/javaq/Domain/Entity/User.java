package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-11.
 */

public class User {
    private String mUserName;
    private String mUserId;

    public User() {
    }

    public User(String mUserName, String mUserId) {
        this.mUserName = mUserName;
        this.mUserId = mUserId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}
