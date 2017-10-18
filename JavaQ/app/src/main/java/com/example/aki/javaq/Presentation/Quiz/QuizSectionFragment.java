package com.example.aki.javaq.Presentation.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aki.javaq.Domain.Helper.SharedPrefRef;
import com.example.aki.javaq.Domain.Usecase.Badge;
import com.example.aki.javaq.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class QuizSectionFragment extends Fragment {
    ListView mListView;
    List<String> mSectionNameList;
    String[] mSectionNameArray;
    SharedPreferences data;
    TextView mQuizListNameTextView;
    ImageView mSectionIcon;
    ImageView mBadgeImageView;
    public static final String PREFS_NAME = "MyPrefsFile";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // reset all shared preferences
      // resetSharedPref();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_section_fragment, container, false);
        data = getContext().getSharedPreferences(QuizFragment.SHARED_PREF_SCORE, Context.MODE_PRIVATE);

        mSectionNameList = new ArrayList<>();
        mSectionNameArray = getResources().getStringArray(R.array.section_list);
        TypedArray mSectionIconArray = getResources().obtainTypedArray(R.array.section_list_icons);

        for (int i = 0; i < mSectionNameArray.length; i++) {
            mSectionNameList.add(mSectionNameArray[i]);
        }

        myArrayAdapter adapter =
                new myArrayAdapter(getActivity(), R.layout.quiz_section_item, mSectionNameList, mSectionIconArray);

        mListView = (ListView) view.findViewById(R.id.quiz_list_view);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("position", position);
                editor.commit();
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }


    public class myArrayAdapter extends ArrayAdapter<String> {
        private int resourceId;
        private List<String> sectionNameList;
        private TypedArray sectionIconList;
        private LayoutInflater inflater;

        public myArrayAdapter(Context context, int resourceId, List<String> sectionNameList, TypedArray mSectionIconArray) {
            super(context, resourceId, sectionNameList);
            this.resourceId = resourceId;
            this.sectionNameList = sectionNameList;
            this.sectionIconList = mSectionIconArray;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView != null) {
                view = convertView;
            } else {
                view = this.inflater.inflate(this.resourceId, null);
            }


            // set texts
            mQuizListNameTextView = (TextView) view.findViewById(R.id.list_name);
            mQuizListNameTextView.setText(this.sectionNameList.get(position));

            TextView mQuizListNumTextView = (TextView) view.findViewById(R.id.list_num);
            mQuizListNumTextView.setText("Quiz " + (position + 1));

            //set section icons
            mSectionIcon = (ImageView) view.findViewById(R.id.icon_section);
            mSectionIcon.setImageDrawable(sectionIconList.getDrawable(position));

            // set badge
            mBadgeImageView = (ImageView) view.findViewById(R.id.section_badge);
            int score = data.getInt(mSectionNameArray[position] + QuizFragment.SHARED_PREF_SCORE_KEY, 0);

            String status = new Badge(score, position).getBadgeStatus();
            switch (status){
                case "gold" :
                    mBadgeImageView.setImageResource(R.drawable.ic_badge_gold);
                    break;
                case "silver" :
                    mBadgeImageView.setImageResource(R.drawable.ic_badge_silver);
                    break;
                case "copper":
                    mBadgeImageView.setImageResource(R.drawable.ic_badge_copper);
                    break;
                case "" :
                    mBadgeImageView.setImageResource(R.drawable.ic_badge_notpassed);
                    break;
                default:
                    mBadgeImageView.setImageResource(R.drawable.ic_badge_notpassed);
                    break;
            }
            return view;
        }
    }

    public void resetSharedPref(){
        SharedPreferences scoreData = getActivity().getSharedPreferences(QuizFragment.SHARED_PREF_SCORE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scoreData.edit();
        editor.clear().commit();

        SharedPreferences progressData = getActivity().getSharedPreferences(SharedPrefRef.SHEARED_PREF_PROGRESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = progressData.edit();
        editor2.clear().commit();

        SharedPreferences data = getActivity().getSharedPreferences(SharedPrefRef.SHEARED_PREF_PROGRESS_LONGEST_DAYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = data.edit();
        editor3.clear().commit();
    }
}


