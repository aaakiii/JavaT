package com.example.aki.javaq;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class ProgressFragment extends Fragment {
    private TextView mActiveStreakTextView;
    private TextView mLongestStreakTextView;
    private int mCountActiveStreak = 0;
    private int mPrevActiveStreak;
    private DayOfWeek dayOfWeek;

    private SharedPreferences mStreakData;
    private SharedPreferences mStreakDataActiveDays;

    public static final String EXTRA_SECTION_POSITON = "aki.javaq_extra_section_position";
    public static final String ACTIVE_STREAK_PREF = "activity_shared_pref";
    public static final String ACTIVE_STREAK_PREF_DAYS = "activity_shared_pref_days";
    public static final String ACTIVE_STREAK_PREV_DAYS = "active_days";
    private int todayDay;
    private int prevDay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStreakData = getActivity().getSharedPreferences(ACTIVE_STREAK_PREF, Context.MODE_PRIVATE);
        mStreakDataActiveDays = getActivity().getSharedPreferences(ACTIVE_STREAK_PREF_DAYS, Context.MODE_PRIVATE);
        dayOfWeek = new DayOfWeek();
        todayDay = dayOfWeek.getIntDay();
        prevDay = todayDay -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment, container, false);

        mActiveStreakTextView = (TextView) view.findViewById(R.id.active_streak);
        mActiveStreakTextView.setText(String.valueOf(getActiveStreakDays()));

        return view;
    }

    private int getActiveStreakDays() {
        SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        mPrevActiveStreak = data.getInt(ACTIVE_STREAK_PREF_DAYS, 0);

        if (mStreakData.getBoolean("Thursday", false)) {
            if (mPrevActiveStreak == 0) {
                mCountActiveStreak++;
                editor.putInt(ACTIVE_STREAK_PREV_DAYS, mCountActiveStreak);
                editor.apply();
            } else {
                mCountActiveStreak = mPrevActiveStreak + 1;
                editor.putInt(ACTIVE_STREAK_PREV_DAYS, mCountActiveStreak);
                editor.apply();
            }
        }
        else {
            mCountActiveStreak = 0;
        }
        return mCountActiveStreak;
    }

//    private int getLatestActiveStreakDays(){
//        SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = data.edit();
//        mPrevActiveStreak = data.getInt(ACTIVE_STREAK_PREF_DAYS, 0);
//
//
//        if(mPrevActiveStreak ==  0){
//            mCountActiveStreak++;
//            editor.putInt(ACTIVE_STREAK_PREV_DAYS, mCountActiveStreak);
//            editor.apply();
//        } else {
//            mCountActiveStreak = mPrevActiveStreak + 1;
//            editor.putInt(ACTIVE_STREAK_PREV_DAYS, mCountActiveStreak);
//            editor.apply();
//        }
//        return mCountActiveStreak;
//    }


}
