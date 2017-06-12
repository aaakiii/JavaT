package com.example.aki.javaq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "com.example.aki.javaq.score";
    private int mScore;
    private TextView mScoreTextView;
    private TextView mScoreCommentTextView;
    private ImageView mScoreBadge;
    private Button mHomeButton;
    private Button mRetryButton;


    public static Intent newIntent(Context context,int score){
        Intent intent = new Intent(context, QuizResultActivity.class);
        intent.putExtra(EXTRA_SCORE, score);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScore = getIntent().getIntExtra(EXTRA_SCORE, 0);
        if(mScore <= 5){
            setContentView(R.layout.quiz_result_failed_fragment);
            mScoreTextView = (TextView) findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));

        }
        else{
            setContentView(R.layout.quiz_result_badge_fragment);
            mScoreTextView = (TextView) findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
            mScoreCommentTextView = (TextView) findViewById(R.id.result_comment);
            mScoreBadge = (ImageView) findViewById(R.id.result_badge);
            if(mScore == 8){
                mScoreCommentTextView.setText("Fantastic!");
                mScoreBadge.setImageResource(R.drawable.badge_gold);
            }
            else if(mScore == 7){
                mScoreCommentTextView.setText("Great!");
                mScoreBadge.setImageResource(R.drawable.badge_silver);
            }
            else{
                mScoreCommentTextView.setText("Good!");
                mScoreBadge.setImageResource(R.drawable.badge_copper);
            }
        }
        mRetryButton = (Button) findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               retryQuestions();
            }
        });
        mHomeButton = (Button)findViewById(R.id.home_button);
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
