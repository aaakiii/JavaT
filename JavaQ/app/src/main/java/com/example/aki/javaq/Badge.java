package com.example.aki.javaq;

/**
 * Created by MinaFujisawa on 2017/06/11.
 */

public class Badge {
    private int mScore;
    private int mSeciontID;
    private int mMaxScore;

    public Badge(int mScore, int mSeciontID) {
        this.mScore = mScore;
        this.mSeciontID = mSeciontID;
        this.mMaxScore = new QuizLab(mSeciontID).getQuizzes().size();
    }

    public String getBadgeStatus() {
        if (mMaxScore * 0.9 <= mScore) {
            return "gold";
        } else if(mMaxScore * 0.8 <= mScore && mScore <= mMaxScore * 0.9){
            return "silver";
        } else if(mMaxScore * 0.7 <= mScore && mScore <= mMaxScore * 0.8){
            return "copper";
        } else if(mScore == 0 ){
            return "";
        } else {
            return "";
        }
    }
}
