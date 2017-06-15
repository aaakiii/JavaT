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
import java.util.Set;

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
    private Set<String> fetch;

    private int mCountActiveStreak = 0;
    private int mPrevActiveStreak;
    private DayOfWeek dayOfWeek;

    private SharedPreferences mStreakData;
    private SharedPreferences mStreakDataActiveDays;
    public static final String ACTIVE_STREAK_PREF_DAYS = "activity_shared_pref_days";
    public static final String EXTRA_SECTION_POSITON = "aki.javaq_extra_section_position";
    public static final String ACTIVE_STREAK_PREF = "activity_shared_pref";
    public static final String PREF_ACTIVE_STREAK_DAYS = "activity_shared_pref_days";
//    public static final String PREF_ACTIVE_STREAK_DAYS = "active_days";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStreakData = getActivity().getSharedPreferences(ACTIVE_STREAK_PREF, Context.MODE_PRIVATE);
        mStreakDataActiveDays = getActivity().getSharedPreferences(ACTIVE_STREAK_PREF_DAYS, Context.MODE_PRIVATE);
       // mStreakData = getActivity().getSharedPreferences(ProgressFragment.ACTIVE_STREAK_PREF, Context.MODE_PRIVATE);

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
        SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefs = data.edit();
        mPrevActiveStreak = data.getInt(ACTIVE_STREAK_PREF_DAYS, 0);
        fetch = mStreakData.getStringSet("key", null);
        setWeeklyProgressColor(fetch);

//        SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
//        mStreakDataActiveDays = data.getInt()
//
//        mActiveStreakTextView.setText(String.valueOf(getActiveStreakDays()));

        return view;
    }


    private void setWeeklyProgressColor(Set<String> fetch) {
        String[] cases = {"1","2","3","4","5","6","7"};
        int i;
        for(i = 1; i < cases.length+1; i++) {
            if (fetch.contains(cases[i]))
                break;

        switch (i) {
            case 1:
                mSunTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mSunTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                mMonTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mMonTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case 3:
                mTueTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mTueTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case 4:
                mWedTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mWedTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case 5:
                mThuTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mThuTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case 6:
                mFriTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mFriTextView.setTextColor(getResources().getColor(R.color.white));
                break;
            case 7:
                mSatTextView.setBackgroundColor(getResources().getColor(R.color.main_color));
                mSatTextView.setTextColor(getResources().getColor(R.color.white));
                break;

//              fetch.remove(cases[i]);

        }
        }
    }
}

