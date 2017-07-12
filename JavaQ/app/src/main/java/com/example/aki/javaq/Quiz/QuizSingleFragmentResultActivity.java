package com.example.aki.javaq.Quiz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.aki.javaq.R;


/**
 * Created by AKI on 2017-06-11.
 */

public abstract class QuizSingleFragmentResultActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.result_fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.result_fragment_container, fragment).commit();
        }
    }
}
