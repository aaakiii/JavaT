package com.example.aki.javaq;


import android.support.v4.app.Fragment;



public class ResultActivity extends SingleFragmentResultActivity{
    @Override
    protected Fragment createFragment() {

        return new ResultFragment();
    }


}

