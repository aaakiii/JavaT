package com.example.aki.javaq.Domain.Entity;

import java.util.UUID;

/**
 * Created by AKI on 2017-07-11.
 */

public class PostMain {
    private String mPostBody;
    private String mUserId;
//    private boolean mPostIsFavod;
    private long mPostTime;

    public PostMain() {
    }

    public PostMain(String mPostBody, String mUserId, long mPostTime) {
        this.mPostBody = mPostBody;
        this.mUserId = mUserId;
        this.mPostTime = mPostTime;
    }

    public String getmPostBody() {
        return mPostBody;
    }

    public void setmPostBody(String mPostBody) {
        this.mPostBody = mPostBody;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public long getmPostTime() {
        return mPostTime;
    }

    public void setmPostTime(long mPostDate) {
        this.mPostTime = mPostDate;
    }
}
