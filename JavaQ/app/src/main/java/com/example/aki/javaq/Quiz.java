package com.example.aki.javaq;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by AKI on 2017-06-06.
 */

public class Quiz implements Serializable{
    private UUID mId;
    private int mQuizNumber;
    private String mSectionName;
    private String mQuestionText;
    private String[] mChoices = new String[3];
    private int mAnswerIndex;

    private static Quiz[] mQuizBank = new Quiz[5];

    public Quiz(int QuizNumber, String SectionName, String QuestionText, String[] Choices, int AnswerIndex){
        mQuizNumber = QuizNumber;
        mSectionName = SectionName;
        mQuestionText = QuestionText;
        mChoices = Choices;
        mAnswerIndex = AnswerIndex;
        mId = UUID.randomUUID();
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

    public String[] getmChoices() {
        return mChoices;
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

    public void setmChoices(String[] mChoices) {
        this.mChoices = mChoices;
    }

    public void setmAnswerIndex(int mAnswerIndex) {
        this.mAnswerIndex = mAnswerIndex;
    }
    public static void init() {
        mQuizBank[0] = new Quiz(0, "Basic Concept", "Which of these lines create a variable?", new String[]{"myAge = 17;", "String myName;", "myName = \"Marty\";"}, 2);
        mQuizBank[1] = new Quiz(1, "Basic Concept", "Which of these lines create a variable?", new String[]{"myAge = 17;", "String myName;", "myName = \"Marty\";"}, 2);
        mQuizBank[2] = new Quiz(2, "Basic Concept", "Which of these lines create a variable?", new String[]{"myAge = 17;", "String myName;", "myName = \"Marty\";"}, 2);
        mQuizBank[3] = new Quiz(3, "Basic Concept", "Which of these lines create a variable?", new String[]{"myAge = 17;", "String myName;", "myName = \"Marty\";"}, 2);
        mQuizBank[4] = new Quiz(4, "Basic Concept", "Which of these lines create a variable?", new String[]{"myAge = 17;", "String myName;", "myName = \"Marty\";"}, 2);


    }

    public static Quiz getQuiz(int num){
        if (num >= mQuizBank.length){
            return null;
        }
        return mQuizBank[num];
    }

}
