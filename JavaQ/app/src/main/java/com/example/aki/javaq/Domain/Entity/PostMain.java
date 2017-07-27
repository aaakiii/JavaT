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
    private int mCommentNum;

    public PostMain() {
    }

    public PostMain(String mPostId, String mUserId, long mPostTime, String mPostBody, String mCommentId) {
        this.mPostId = mPostId;
        this.mUserId = mUserId;
        this.mPostTime = mPostTime;
        this.mPostBody = mPostBody;
        this.mCommentId = mCommentId;
    }

    public int getCommentNum() {
        return mCommentNum;
    }

    public void setCommentNum(int commentNum) {
        mCommentNum = commentNum;
    }

    public String getPostId() {
        return mPostId;
    }

    public void setPostId(String mPostId) {
        this.mPostId = mPostId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public long getPostTime() {
        return mPostTime;
    }

    public void setPostTime(long mPostTime) {
        this.mPostTime = mPostTime;
    }

    public String getPostBody() {
        return mPostBody;
    }

    public void setPostBody(String mPostBody) {
        this.mPostBody = mPostBody;
    }

    public String getmCommentId() {
        return mCommentId;
    }

    public void setmCommentId(String mCommentId) {
        this.mCommentId = mCommentId;
    }
}
