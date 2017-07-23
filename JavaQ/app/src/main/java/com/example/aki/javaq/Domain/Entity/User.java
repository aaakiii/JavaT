package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-11.
 */

public class User {
    private String mUserName;
    private String mUserPicUri;
    private String mUid;

    public User() {
    }

    public User(String mUserName, String mUserPicUri, String mUid) {
        this.mUserName = mUserName;
        this.mUserPicUri = mUserPicUri;
        this.mUid = mUid;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserPicUri() {
        return mUserPicUri;
    }

    public void setmUserPicUri(String mUserPicUri) {
        this.mUserPicUri = mUserPicUri;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }
}
