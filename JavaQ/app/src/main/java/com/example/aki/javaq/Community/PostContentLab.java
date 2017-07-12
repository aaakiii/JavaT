package com.example.aki.javaq.Community;

/**
 * Created by AKI on 2017-07-11.
 */

public class PostContentLab {
    private String mComId;
    private String mComText;
    private String mComComment;
    private String mUserName;
    private String mUserImageUrl;
    private boolean mComIsFavoed;
    private int mComLike;
    private int mComUnlike;

    public PostContentLab(){

    }

    public PostContentLab(String text,String comment, String name, String imageUrl, boolean isFavoed, int like, int unlike){
        mComText = text;
        mComComment = comment;
        mUserName = name;
        mUserImageUrl = imageUrl;
        mComIsFavoed = isFavoed;
        mComLike = like;
        mComUnlike = unlike;
    }

    public String getComment() {
        return mComComment;
    }

    public void setComment(String comment) {
        mComComment = comment;
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

    public String getImageUrl() {
        return mUserImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mUserImageUrl = imageUrl;
    }

    public boolean isFavoed() {
        return mComIsFavoed;
    }

    public void setFavoed(boolean favoed) {
        mComIsFavoed = favoed;
    }

    public int getLike() {
        return mComLike;
    }

    public void setLike(int like) {
        mComLike = like;
    }

    public int getUnlike() {
        return mComUnlike;
    }

    public void setUnlike(int unlike) {
        mComUnlike = unlike;
    }
}
