package com.example.aki.javaq;

import android.support.v4.app.Fragment;

public class QuizActivity extends SingleFragmentQuizActivity {
    @Override
    protected Fragment createFragment(){
        return new QuizFragment();
    }
}
