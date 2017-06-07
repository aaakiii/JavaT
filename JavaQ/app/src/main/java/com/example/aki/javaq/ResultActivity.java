package com.example.aki.javaq;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

    }
    //Retry　button押したら
    public void retryQuestions(View view){
        Intent intent = new Intent(getApplication(), QuizActivity.class);
        startActivity(intent);
    }
    //Back to Home button 押したら
    public void backToSection(View view){
        //sectionのクラス名確認後変更
        Intent intent = new Intent(getApplication(), QuizActivity.class);
        startActivity(intent);
    }


}
