package com.example.aki.javaq;


import java.util.UUID;

/**
 * Created by AKI on 2017-06-06.
 */

public class Quiz{
    private UUID mId;
    private int mQuizNumber;
    private String mSectionName;
    private String mQuestionText;
    private String mFirstChoice;
    private String mSecondChoice;
    private String mThirdChoice;
    private int mAnswerIndex;

    public Quiz(int QuizNumber, String SectionName, String QuestionText,String FirstChoice, String SecondChoice,String ThirdChoice,int AnswerIndex){
        mId = UUID.randomUUID();
        mQuizNumber = QuizNumber;
        mSectionName = SectionName;
        mQuestionText = QuestionText;
        mFirstChoice = FirstChoice;
        mSecondChoice = SecondChoice;
        mThirdChoice = ThirdChoice;
        mAnswerIndex = AnswerIndex;
    }
    public UUID getId(){
        return mId;
    }
    public int getmQuizNumber() {
        return mQuizNumber;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmQuestionText() {
        return mQuestionText;
    }

    public String getmFirstChoice() {
        return mFirstChoice;
    }

    public String getmSecondChoice() {
        return mSecondChoice;
    }

    public String getmThirdChoice() {
        return mThirdChoice;
    }

    public int getmAnswerIndex() {
        return mAnswerIndex;
    }

    public void setmQuizNumber(int mQuizNumber) {
        this.mQuizNumber = mQuizNumber;
    }

    public void setmSectionName(String mSectionName) {
        this.mSectionName = mSectionName;
    }

    public void setmQuestionText(String mQuestionText) {
        this.mQuestionText = mQuestionText;
    }

    public void setmFirstChoice(String mFirstChoice) {
        this.mFirstChoice = mFirstChoice;
    }

    public void setmSecondChoice(String mSecondChoice) {
        this.mSecondChoice = mSecondChoice;
    }

    public void setmThirsChoice(String mThirsChoice) {
        this.mThirdChoice = mThirsChoice;
    }

    public void setmAnswerIndex(int mAnswerIndex) {
        this.mAnswerIndex = mAnswerIndex;
    }

}
