package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-13.
 */

public class PostCommentContents {
    private String mPostId;
    private String mComBody;
    private int mComLike;
    private int mComUnlike;
    private String mUserId;
    private long mPostTime;

    public PostCommentContents(){

    }

    public PostCommentContents(String mComBody, String mUserId,long mPostTime, int like, int unLike){
        this.mComBody = mComBody;
        this.mUserId = mUserId;
        mComLike = like;
        mComUnlike = unLike;
        this.mPostTime = mPostTime;

    }



    public String getComBody() {
        return mComBody;
    }

    public void setComBody(String mComBody) {
        this.mComBody = mComBody;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserName) {
        this.mUserId = mUserId;
    }
    public long getmPostTime() {
        return mPostTime;
    }

    public void setmPostTime(long mPostTime) {
        this.mPostTime = mPostTime;
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
}
