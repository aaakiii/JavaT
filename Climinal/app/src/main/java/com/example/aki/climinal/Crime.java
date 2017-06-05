package com.example.aki.climinal;

import java.util.Date;
import java.util.UUID;

/**
 * Created by AKI on 2017-05-31.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice = false;
    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public boolean isRequiresPolice(){
        return mRequiresPolice;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    public void setDate(Date date) {
        mDate = date;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
    public void setRequiresPolice(boolean required){
        mRequiresPolice = required;
    }

}
