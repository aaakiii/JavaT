package com.example.aki.javaq.Domain.Entity;

import java.util.UUID;

/**
 * Created by AKI on 2017-07-11.
 */

public class User {
    private UUID mUserId;
    private String mUserName;
    private String mUserIcon;


    public User(String mUserName, String mUserIcon) {
        this.mUserName = mUserName;
        this.mUserIcon = mUserIcon;
        mUserId = UUID.randomUUID();
    }

    public UUID getmUserId() {
        return mUserId;
    }

    public void setmUserId(UUID mUserId) {
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
