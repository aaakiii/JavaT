package com.example.aki.javaq.Quiz;

import android.support.v4.app.Fragment;

public class QuizActivity extends QuizSingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new QuizFragment();
    }
}
