package com.example.aki.javaq.Presentation.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aki.javaq.Domain.Usecase.Badge;
import com.example.aki.javaq.Domain.Usecase.DayOfWeek;
import com.example.aki.javaq.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class QuizResultFragment extends Fragment {
    public static final String EXTRA_SCORE = "com.example.aki.javaq.score";
    private static final String EXTRA_QUIZZES = "com.example.aki.javaq.quizzes";
    public static final String SHEARED_PREF_PROGRESS = "shared_pref_progress";
    public static final String SHEARED_PREF_PROGRESS_ACTIVE_DAYS = "shared_pref_progress_active_days";
    public static final String SHEARED_PREF_PROGRESS_ACTIVE_TIME_STAMP = "shared_pref_progress_active_time_stamp";
    public static final String SHEARED_PREF_PROGRESS_WEEKLY = "shared_pref_progress_weekly";
    public static final String SHEARED_PREF_PROGRESS_LONGEST_DAYS = "shared_pref_progress_previous_days";
    private int mScore;
    private TextView mScoreTextView;
    private TextView mScoreCommentTextView;
    private ImageView mScoreBadge;
    private TextView mScoreDenominator;
    private int mQuizzesNumber;
    private int mCurrentSectionID;
    private SharedPreferences mAcStreakShearedPref;
    private SharedPreferences.Editor editor;
    private DayOfWeek dayOfWeek;
    private boolean isUsedYesterday = true;
    private int mPreviousCountAccess;
    private int mCountAccess;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAcStreakShearedPref = getActivity().getSharedPreferences(SHEARED_PREF_PROGRESS, Context.MODE_PRIVATE);
        editor = mAcStreakShearedPref.edit();
        countStreak(checkOnceParDay());
        weeklyProgress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        mScore = intent.getIntExtra(EXTRA_SCORE, 0);
        mQuizzesNumber = intent.getIntExtra(EXTRA_QUIZZES, 0);
        mCurrentSectionID = intent.getIntExtra(QuizFragment.EXTRA_CURRENT_SECTION_ID, 0);
        View v;

        String status = new Badge(mScore, mCurrentSectionID).getBadgeStatus();

        // change inflate by status
        if (status != "") {
            v = inflater.inflate(R.layout.quiz_result_badge_fragment, container, false);
            mScoreDenominator = (TextView) v.findViewById(R.id.result_score_denominator);
            mScoreDenominator.setText(String.valueOf(mQuizzesNumber));
            mScoreTextView = (TextView) v.findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
            mScoreCommentTextView = (TextView) v.findViewById(R.id.result_comment);
            mScoreBadge = (ImageView) v.findViewById(R.id.result_badge);

            // set badges and comments
            switch (status) {
                case "gold":
                    mScoreCommentTextView.setText("Fantastic!");
                    mScoreBadge.setImageResource(R.drawable.badge_gold);
                    break;
                case "silver":
                    mScoreCommentTextView.setText("Great!");
                    mScoreBadge.setImageResource(R.drawable.badge_silver);
                    break;
                case "copper":
                    mScoreCommentTextView.setText("Good!");
                    mScoreBadge.setImageResource(R.drawable.badge_copper);
                    break;
            }
        } else {
            v = inflater.inflate(R.layout.quiz_result_failed_fragment, container, false);
            mScoreDenominator = (TextView) v.findViewById(R.id.result_score_denominator);
            mScoreDenominator.setText(String.valueOf(mQuizzesNumber));
            mScoreTextView = (TextView) v.findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
        }
        return v;
    }

    private void countStreak(boolean checkOnceParDay) {

        editor.putLong(SHEARED_PREF_PROGRESS_ACTIVE_TIME_STAMP, System.currentTimeMillis());
        editor.commit();

        if (checkOnceParDay) {
            mCountAccess = mAcStreakShearedPref.getInt(SHEARED_PREF_PROGRESS_ACTIVE_DAYS, 0);
            mCountAccess++;
            editor.putInt(SHEARED_PREF_PROGRESS_ACTIVE_DAYS, mCountAccess);
            editor.commit();
//            Toast.makeText(getActivity(), "Active streak : " + String.valueOf(mCountAccess), Toast.LENGTH_SHORT).show();
        } else {
            // reset to 1
            if (!isUsedYesterday) {
                editor.remove(SHEARED_PREF_PROGRESS_ACTIVE_DAYS).commit();
                editor.putInt(SHEARED_PREF_PROGRESS_ACTIVE_DAYS, 1);
                editor.commit();
//                Toast.makeText(getActivity(), "reset to 1", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(getActivity(), "we already counted today", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkOnceParDay() {
        long lastCheckedMillis = mAcStreakShearedPref.getLong(SHEARED_PREF_PROGRESS_ACTIVE_TIME_STAMP, 0);
        editor.putLong(SHEARED_PREF_PROGRESS_ACTIVE_TIME_STAMP, lastCheckedMillis);
        long now = System.currentTimeMillis();

        // tomorrow at midnight
        Calendar date = new GregorianCalendar();
        date.setTime(new Date(lastCheckedMillis));
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DAY_OF_MONTH, 1); //next day
        long nextMidnight = date.getTimeInMillis();

        date.add(Calendar.DAY_OF_MONTH, 1); //next day
        long tomorrowMidnight = date.getTimeInMillis();

        if (nextMidnight < now && now < tomorrowMidnight) {
            return true;
        } else if (lastCheckedMillis == 0) {
            return true;
        } else if (tomorrowMidnight < now) {
            isUsedYesterday = false;
            return false;
        } else {
            return false;
        }
    }

    private void weeklyProgress() {
        dayOfWeek = new DayOfWeek();

        for (int i = 1; i <= 7; i++) {
            if(dayOfWeek.getIntDay() == i){
                editor.putBoolean(SHEARED_PREF_PROGRESS_WEEKLY + String.valueOf(i), true);
            }
        }
        editor.commit();
    }
}

