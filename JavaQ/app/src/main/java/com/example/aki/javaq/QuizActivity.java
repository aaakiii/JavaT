package com.example.aki.javaq;


import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class QuizActivity extends SingleFragmentQuizActivity {


    @Override
    protected Fragment createFragment(){
        return new QuizFragment();
    }


}
