package com.example.aki.javaq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by AKI on 2017-06-11.
 */

public abstract class QuizSingleFragmentResultActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    private Button mHomeButton;
    private Button mRetryButton;

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
    //Retry　button押したら
    public void retryQuestions(){
        Intent intent = new Intent(getApplication(), QuizActivity.class);
        startActivity(intent);
    }
    //Back to Home button 押したら
    public void backToSection(){
        Intent intent = new Intent(getApplication(), QuizSectionActivity.class);
        startActivity(intent);
    }


}
