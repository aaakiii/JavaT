package com.example.aki.javaq.Quiz;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aki.javaq.DrawerMenuActivity;
import com.example.aki.javaq.R;

public class QuizSectionActivity extends AppCompatActivity {
//    public QuizSectionActivity() {
//        super(R.layout.quiz_section_fragment, R.id.quiz_list_fragment_container);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_section_activity);

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.progress_fragment_container);
//
//        if (fragment == null) {
//            fragment = new CommunityListFragment();
//            fm.beginTransaction().add(R.id.progress_fragment_container, fragment).commit();
//        }
    }
}
