package com.example.aki.javaq;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class QuizSectionSingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_section_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.quiz_list_fragment_container);


        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.quiz_list_fragment_container, fragment).commit();
        }

    }

}