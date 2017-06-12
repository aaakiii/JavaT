package com.example.aki.javaq;


import android.support.v4.app.Fragment;



public class QuizResultActivity extends SingleFragmentResultActivity{
    @Override
    protected Fragment createFragment() {

        return new ResultFragment();
    }


}

