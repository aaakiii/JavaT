package com.example.aki.javaq.Domain.Entity;

import java.util.UUID;

/**
 * Created by AKI on 2017-07-11.
 */

public class PostMain {
    private String mPostId;
    private String mUserId;
    //    private boolean mPostIsFavod;
    private long mPostTime;
    private String mPostBody;
    private String mCommentId;

    public PostMain() {
    }

    public PostMain(String mPostId, String mUserId, long mPostTime, String mPostBody, String mCommentId) {
        this.mPostId = mPostId;
        this.mUserId = mUserId;
        this.mPostTime = mPostTime;
        this.mPostBody = mPostBody;
        this.mCommentId = mCommentId;
    }

    public String getmPostId() {
        return mPostId;
    }

    public void setmPostId(String mPostId) {
        this.mPostId = mPostId;
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

    public void setmPostTime(long mPostTime) {
        this.mPostTime = mPostTime;
    }

    public String getmPostBody() {
        return mPostBody;
    }

    public void setmPostBody(String mPostBody) {
        this.mPostBody = mPostBody;
    }

    public String getmCommentId() {
        return mCommentId;
    }

    public void setmCommentId(String mCommentId) {
        this.mCommentId = mCommentId;
    }
}
