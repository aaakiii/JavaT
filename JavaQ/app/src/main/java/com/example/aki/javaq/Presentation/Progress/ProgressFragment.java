package com.example.aki.javaq.Presentation.Progress;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aki.javaq.Domain.Helper.SharedPrefRef;
import com.example.aki.javaq.Domain.Usecase.DayOfWeek;
import com.example.aki.javaq.Presentation.Quiz.QuizResultFragment;
import com.example.aki.javaq.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class ProgressFragment extends Fragment {
    private TextView mActiveStreakTextView;
    private TextView mLongestStreakTextView;
    private TextView mMonTextView;
    private TextView mTueTextView;
    private TextView mWedTextView;
    private TextView mThuTextView;
    private TextView mFriTextView;
    private TextView mSatTextView;
    private TextView mSunTextView;

    private SharedPreferences mProgressShearedPref;
    private SharedPreferences.Editor editor;
    private int mLongestDays = 0;
    private int mFirstLongestDays;
    private String KEY = "key";
    private String SAVE = "save";

    private List<TextView> mWeeklyTextViews;

    private long ONE_DAY_MILLIS = 86400000;
    private long lastCheckedMillis;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check reset
        SharedPreferences mProgressShearedPref = getActivity().getSharedPreferences(SharedPrefRef.SHEARED_PREF_PROGRESS, Context.MODE_PRIVATE);
        lastCheckedMillis = mProgressShearedPref.getLong(SharedPrefRef.SHEARED_PREF_PROGRESS_ACTIVE_TIME_STAMP, 0);
        long now = System.currentTimeMillis();

        if (now - lastCheckedMillis > ONE_DAY_MILLIS * 7) {
            resetSharedPrefForWeeklyProgress();
            Toast.makeText(getActivity(), "reset", Toast.LENGTH_SHORT).show();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(lastCheckedMillis);
            int lastDOF = calendar.get(Calendar.DAY_OF_WEEK);

            if (getNextMidnightMillis() - lastCheckedMillis + ONE_DAY_MILLIS * (7 - lastDOF) < now - lastCheckedMillis) {
                resetSharedPrefForWeeklyProgress();
                Toast.makeText(getActivity(), "reset", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment, container, false);

        mLongestStreakTextView = (TextView) view.findViewById(R.id.longest_streak);
        mActiveStreakTextView = (TextView) view.findViewById(R.id.active_streak);
        mProgressShearedPref = getActivity().getSharedPreferences(SharedPrefRef.SHEARED_PREF_PROGRESS, Context.MODE_PRIVATE);
        editor = mProgressShearedPref.edit();


        // set active streak
        if (isUsedPreviousDay(lastCheckedMillis) && lastCheckedMillis > 0) {
            editor.remove(SharedPrefRef.SHEARED_PREF_PROGRESS_ACTIVE_DAYS).commit();
        }
        int activeDays = mProgressShearedPref.getInt(SharedPrefRef.SHEARED_PREF_PROGRESS_ACTIVE_DAYS, 0);
        mActiveStreakTextView.setText(String.valueOf(activeDays));

        //set longest streak

        SharedPreferences data = getActivity().getSharedPreferences(SharedPrefRef.SHEARED_PREF_PROGRESS_LONGEST_DAYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        mFirstLongestDays = 0;
        mFirstLongestDays = data.getInt(SAVE, 0);
        if (mFirstLongestDays < activeDays) {
            mFirstLongestDays = activeDays + 1;
            mLongestDays = activeDays;
            editor.putInt(KEY, mLongestDays);
            editor.putInt(SAVE, mFirstLongestDays);
            editor.apply();
            mLongestStreakTextView.setText(String.valueOf(mLongestDays));
        } else {

            mLongestDays = data.getInt(KEY, 0);
            if (mLongestDays >= activeDays) {
                editor.putInt(KEY, mLongestDays);
                editor.apply();
                mLongestStreakTextView.setText(String.valueOf(mLongestDays));
            } else if (mLongestDays <= activeDays) {
                mLongestDays = activeDays;
                editor.putInt(KEY, mLongestDays);
                editor.apply();
                mLongestStreakTextView.setText(String.valueOf(mLongestDays));
            }
        }

        mSunTextView = (TextView) view.findViewById(R.id.weekly_sun);
        mMonTextView = (TextView) view.findViewById(R.id.weekly_mon);
        mTueTextView = (TextView) view.findViewById(R.id.weekly_tue);
        mWedTextView = (TextView) view.findViewById(R.id.weekly_wed);
        mThuTextView = (TextView) view.findViewById(R.id.weekly_thu);
        mFriTextView = (TextView) view.findViewById(R.id.weekly_fri);
        mSatTextView = (TextView) view.findViewById(R.id.weekly_sta);

        mWeeklyTextViews = new ArrayList<>();
        mWeeklyTextViews.add(mSunTextView);
        mWeeklyTextViews.add(mMonTextView);
        mWeeklyTextViews.add(mTueTextView);
        mWeeklyTextViews.add(mWedTextView);
        mWeeklyTextViews.add(mThuTextView);
        mWeeklyTextViews.add(mFriTextView);
        mWeeklyTextViews.add(mSatTextView);


        //set weekly color
        for (int i = 1; i <= 7; i++) {
            if (mProgressShearedPref.getBoolean(SharedPrefRef.SHEARED_PREF_PROGRESS_WEEKLY + String.valueOf(i), false) == true) {
                setColorForWeekly(i);
            } else {
                resetColorForWeekly(i);
            }
        }


        return view;
    }


    private boolean isUsedPreviousDay(long lastCheckedMillis) {
        long now = System.currentTimeMillis();
        // tomorrow at midnight
        Calendar date = new GregorianCalendar();
        date.setTime(new Date(lastCheckedMillis));
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DAY_OF_MONTH, 2); //next day
        long tomorrowMidnight = date.getTimeInMillis();
        if (tomorrowMidnight < now) {
            return true;
        } else {
            return false;
        }
    }



    private void setColorForWeekly(int trueDay) {
        mWeeklyTextViews.get(trueDay - 1).setBackgroundColor(getResources().getColor(R.color.main_color));
        mWeeklyTextViews.get(trueDay - 1).setTextColor(getResources().getColor(R.color.white));
    }

    private void resetColorForWeekly(int falseDay) {
        mWeeklyTextViews.get(falseDay - 1).setBackgroundColor(getResources().getColor(R.color.light_gray));
        mWeeklyTextViews.get(falseDay - 1).setTextColor(getResources().getColor(R.color.main_text));
    }

    private void resetSharedPrefForWeeklyProgress() {
        SharedPreferences progressData = getActivity().getSharedPreferences(SharedPrefRef.SHEARED_PREF_PROGRESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = progressData.edit();
        editor.clear().commit();
    }

    private long getNextMidnightMillis() {
        Calendar date = new GregorianCalendar();
        date.setTime(new Date(lastCheckedMillis));
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DAY_OF_MONTH, 1); //next day
        return date.getTimeInMillis();
    }


}


