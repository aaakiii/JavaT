package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-13.
 */

public class PostComment {
    private String mComId;
    private String mPostId;
    private String mComBody;
    private long mComTime;
    private int mComLike;
    private int mComUnlike;
    private boolean isLikeTapped;
    private boolean isUnlikeTapped;
    private String mUserId;

    public PostComment(){

    }

    public PostComment(String mComId, String mPostId, String mComBody, long mComTime, int mComLike, int mComUnlike, boolean isLikeTapped, boolean isUnlikeTapped, String mUserId) {
        this.mComId = mComId;
        this.mPostId = mPostId;
        this.mComBody = mComBody;
        this.mComTime = mComTime;
        this.mComLike = mComLike;
        this.mComUnlike = mComUnlike;
        this.isLikeTapped = isLikeTapped;
        this.isUnlikeTapped = isUnlikeTapped;
        this.mUserId = mUserId;
    }

    public String getComId() {
        return mComId;
    }

    public void setComId(String mComId) {
        this.mComId = mComId;
    }

    public String getPostId() {
        return mPostId;
    }

    public void setPostId(String mPostId) {
        this.mPostId = mPostId;
    }

    public String getComBody() {
        return mComBody;
    }

    public void setComBody(String mComBody) {
        this.mComBody = mComBody;
    }

    public long getComTime() {
        return mComTime;
    }

    public void setComTime(long mComTime) {
        this.mComTime = mComTime;
    }

    public int getComLike() {
        return mComLike;
    }

    public void setComLike(int mComLike) {
        this.mComLike = mComLike;
    }

    public int getComUnlike() {
        return mComUnlike;
    }

    public void setComUnlike(int mComUnlike) {
        this.mComUnlike = mComUnlike;
    }

    public boolean isLikeTapped() {
        return isLikeTapped;
    }

    public void setLikeTapped(boolean likeTapped) {
        isLikeTapped = likeTapped;
    }

    public boolean isUnlikeTapped() {
        return isUnlikeTapped;
    }

    public void setUnlikeTapped(boolean unlikeTapped) {
        isUnlikeTapped = unlikeTapped;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}
