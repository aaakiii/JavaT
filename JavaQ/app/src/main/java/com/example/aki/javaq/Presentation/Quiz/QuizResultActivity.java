package com.example.aki.javaq.Presentation.Quiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.aki.javaq.Presentation.SingleFragmentActivity;
import com.example.aki.javaq.R;


public class QuizResultActivity extends AppCompatActivity {
    private Button mHomeButton;
    private Button mRetryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new QuizResultFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }



        mRetryButton = (Button) findViewById(R.id.btn_retry);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                retryQuestions();
            }
        });
        mHomeButton = (Button)findViewById(R.id.btn_back_home);
        mHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                backToSection();
            }
        });

    }
    //Back to Home button
    public void backToSection(){
        Intent intent = new Intent(getApplication(), QuizSectionActivity.class);
        startActivity(intent);
    }
    //Retry button
    public void retryQuestions(){
        Intent intent = new Intent(getApplication(), QuizActivity.class);
        startActivity(intent);
    }
}

