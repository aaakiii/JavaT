package com.example.aki.javaq.Domain.Entity;

import java.util.UUID;

/**
 * Created by AKI on 2017-07-11.
 */

public class PostMainContents {
    private UUID mComId;
    private String mComText;
    private String mUserName;
    private boolean mComIsFavod;
    private String mImageUrl;
    private String mComDate;
    private int mCommentsNum;
    private String mId;


    public PostMainContents(){
        this(UUID.randomUUID());

    }
    public PostMainContents(UUID id){
        mComId = id;
    }

    public PostMainContents(String text, String name, boolean isFavoed, String imageUrl,String date, int commentsNum){
//        , String date, int commentsNum
        mComText = text;
        mUserName = name;
        mComIsFavod = isFavoed;
        mImageUrl = imageUrl;
        mComDate = date;
        mCommentsNum = commentsNum;

    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public int getCommentsNum() {
        return mCommentsNum;
    }


    public UUID getUUId() {
        return mComId;
    }
    public void setCommentsNum(int mCommentsNum) {
        this.mCommentsNum = mCommentsNum;
    }

    public String getDate() {
        return mComDate;
    }

    public void setDate(String mComDate) {
        this.mComDate = mComDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }



    public String getText() {
        return mComText;
    }

    public void setText(String text) {
        mComText = text;
    }

    public String getName() {
        return mUserName;
    }

    public void setName(String name) {
        mUserName = name;
    }


    public boolean isFavoed() {
        return mComIsFavod;
    }

    public void setFavoed(boolean favoed) {
        mComIsFavod = favoed;
    }

}
