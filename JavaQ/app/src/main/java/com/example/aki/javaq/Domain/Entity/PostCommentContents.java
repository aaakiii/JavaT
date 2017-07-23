package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-13.
 */

public class PostCommentContents {
    private String mComId;
    private String mComComment;
    private String mUserName;
    private int mComLike;
    private int mComUnlike;

    public PostCommentContents(){

    }

    public PostCommentContents(String comment, String name, int like, int unLike){
        mComComment = comment;
        mUserName = name;
        mComLike = like;
        mComUnlike = unLike;

    }

    public String getComId() {
        return mComId;
    }

    public void setComId(String mComId) {
        this.mComId = mComId;
    }

    public String getComComment() {
        return mComComment;
    }

    public void setComComment(String mComComment) {
        this.mComComment = mComComment;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
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
