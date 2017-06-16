package com.example.aki.javaq;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;


public class QuizResultActivity extends QuizSingleFragmentResultActivity {
    private Button mHomeButton;
    private Button mRetryButton;

    @Override
    protected Fragment createFragment() {
        return new QuizResultFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

