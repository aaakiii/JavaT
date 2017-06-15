package com.example.aki.javaq;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class QuizResultFragment extends Fragment {
    public static final String EXTRA_SCORE = "com.example.aki.javaq.score";
    private static final String EXTRA_QUIZZES = "com.example.aki.javaq.quizzes";
    private int mScore;
    private TextView mScoreTextView;
    private TextView mScoreCommentTextView;
    private ImageView mScoreBadge;
    private TextView mScoreDenominator;
    private int mQuizzesNumber;
    private SharedPreferences mStreakData;
    private DayOfWeek dayOfWeek;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStreakData = getActivity().getSharedPreferences(ProgressFragment.ACTIVE_STREAK_PREF, Context.MODE_PRIVATE);
        countStreak();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        mScore = intent.getIntExtra(EXTRA_SCORE, 0);
        mQuizzesNumber = intent.getIntExtra(EXTRA_QUIZZES, 0);
        View v;

        if (mScore <= 5) {
            v = inflater.inflate(R.layout.quiz_result_failed_fragment, container, false);
            mScoreDenominator = (TextView) v.findViewById(R.id.result_score_denominator);
            mScoreDenominator.setText(String.valueOf(mQuizzesNumber));
            mScoreTextView = (TextView) v.findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
        } else {
            v = inflater.inflate(R.layout.quiz_result_badge_fragment, container, false);
            mScoreDenominator = (TextView) v.findViewById(R.id.result_score_denominator);
            mScoreDenominator.setText(String.valueOf(mQuizzesNumber));
            mScoreTextView = (TextView) v.findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
            mScoreCommentTextView = (TextView) v.findViewById(R.id.result_comment);
            mScoreBadge = (ImageView) v.findViewById(R.id.result_badge);
            if (mScore == 8) {
                mScoreCommentTextView.setText("Fantastic!");
                mScoreBadge.setImageResource(R.drawable.badge_gold);
            } else if (mScore == 7) {
                mScoreCommentTextView.setText("Great!");
                mScoreBadge.setImageResource(R.drawable.badge_silver);
            } else {
                mScoreCommentTextView.setText("Good!");
                mScoreBadge.setImageResource(R.drawable.badge_copper);
            }
        }

        return v;
    }

    private void countStreak() {
        dayOfWeek = new DayOfWeek();
        SharedPreferences.Editor editor = mStreakData.edit();
        editor.putBoolean("Thursday", true);
        Log.d("log", "dayOfWeek.getDay() : " + dayOfWeek.getDay()+ dayOfWeek.getIntDay());
        editor.apply();
    }

}

