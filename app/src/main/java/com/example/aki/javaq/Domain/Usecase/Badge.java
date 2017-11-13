package com.example.aki.javaq.Domain.Usecase;


/**
 * Created by MinaFujisawa on 2017/06/11.
 */

public class Badge {
    private int mScore;
    private int mMaxScore;

    public Badge(int mScore, int mSectionID) {
        this.mScore = mScore;
        this.mMaxScore = new QuizLab(mSectionID).getQuizzes().size();
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
