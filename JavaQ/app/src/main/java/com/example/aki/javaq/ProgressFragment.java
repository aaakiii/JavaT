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
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    private int mCountActiveStreak = 0;
    private int mPrevActiveStreak;
    private DayOfWeek dayOfWeek;

    private SharedPreferences mStreakData;
    private SharedPreferences mStreakDataActiveDays;

    public static final String EXTRA_SECTION_POSITON = "aki.javaq_extra_section_position";
    public static final String ACTIVE_STREAK_PREF = "activity_shared_pref";
    public static final String PREF_ACTIVE_STREAK_DAYS = "activity_shared_pref_days";
//    public static final String PREF_ACTIVE_STREAK_DAYS = "active_days";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mStreakData = getActivity().getSharedPreferences(ACTIVE_STREAK_PREF, Context.MODE_PRIVATE);
//        mStreakDataActiveDays = getActivity().getSharedPreferences(ACTIVE_STREAK_PREF_DAYS, Context.MODE_PRIVATE);
        dayOfWeek = new DayOfWeek();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment, container, false);
        mMonTextView = (TextView) view.findViewById(R.id.weekly_mon);
        mTueTextView = (TextView) view.findViewById(R.id.weekly_tue);
        mWedTextView = (TextView) view.findViewById(R.id.weekly_wed);
        mThuTextView = (TextView) view.findViewById(R.id.weekly_thu);
        mFriTextView = (TextView) view.findViewById(R.id.weekly_fri);
        mSatTextView = (TextView) view.findViewById(R.id.weekly_sta);
        mSunTextView = (TextView) view.findViewById(R.id.weekly_sun);

        mActiveStreakTextView = (TextView) view.findViewById(R.id.active_streak);

//        SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
//        mStreakDataActiveDays = data.getInt()
//
//        mActiveStreakTextView.setText(String.valueOf(getActiveStreakDays()));

        return view;
    }



    private void setWeeklyProgressColor(String day) {
        switch (day) {
            case "Monday":
                mMonTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case "Tuesday":
                mTueTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case "Wednesday":
                mWedTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case "Thursday":
                mThuTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case "Friday":
                mFriTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case "Saturday":
                mSatTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case "Sunday":
                mSunTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }


}


