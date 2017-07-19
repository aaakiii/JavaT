package com.example.aki.javaq.Domain.Entity;

/**
 * Created by AKI on 2017-07-11.
 */

public class PostMainContents {
    private String mComId;
    private String mComText;
    private String mUserName;
    private boolean mComIsFavod;
    private String mImageUrl;


    public PostMainContents(){

    }

    public PostMainContents(String text, String name, boolean isFavoed, String imageUrl){
        mComText = text;
        mUserName = name;
        mComIsFavod = isFavoed;
        mImageUrl = imageUrl;

    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getId() {
        return mComId;
    }

    public void setId(String id) {
        mComId = id;
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
