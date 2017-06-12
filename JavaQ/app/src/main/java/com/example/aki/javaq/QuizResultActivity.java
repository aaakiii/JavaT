package com.example.aki.javaq;


import android.support.v4.app.Fragment;



public class QuizResultActivity extends QuizSingleFragmentResultActivity {
    @Override
    protected Fragment createFragment() {

        return new QuizResultFragment();
    }


}

