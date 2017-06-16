package com.example.aki.javaq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class QuizSectionFragment extends Fragment {
    ListView mListView;
    List<String> mSectionArrayList;
    String[] mSectionList;
    SharedPreferences data;
    TextView mQuizListNameTextView;
    ImageView mBadgeImageView;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_section_fragment, container, false);
        data = getContext().getSharedPreferences("DataSave", Context.MODE_PRIVATE);

        mSectionArrayList = new ArrayList<>();
        mSectionList = getResources().getStringArray(R.array.section_list);

        for (int i = 0; i < mSectionList.length; i++) {
            mSectionArrayList.add(mSectionList[i]);
        }

        myArrayAdapter adapter =
                new myArrayAdapter(getActivity(), R.layout.quiz_section_item, mSectionArrayList);

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
        private List<String> items;
        private LayoutInflater inflater;

        public myArrayAdapter(Context context, int resourceId, List<String> items) {
            super(context, resourceId, items);
            this.resourceId = resourceId;
            this.items = items;
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

            String item = this.items.get(position);

            // set texts
            mQuizListNameTextView = (TextView) view.findViewById(R.id.list_name);
            mQuizListNameTextView.setText(item);

            TextView mQuizListNumTextView = (TextView) view.findViewById(R.id.list_num);
            mQuizListNumTextView.setText("Quiz " + (position + 1));


            // set badge
            mBadgeImageView = (ImageView) view.findViewById(R.id.section_badge);
            int score = data.getInt(mSectionList[position] + QuizFragment.KEYWORD_PREF_SCORE, 0);

            String status = new Badge(score, position).getBadgeStatus();
            switch (status){
                case "gold" :
                    mBadgeImageView.setImageResource(R.drawable.badge_gold);
                    break;
                case "silver" :
                    mBadgeImageView.setImageResource(R.drawable.badge_silver);
                    break;
                case "copper":
                    mBadgeImageView.setImageResource(R.drawable.badge_copper);
                    break;
                case "" :
                    mBadgeImageView.setImageResource(R.drawable.badge_not_passed);
                    break;
                default:
                    mBadgeImageView.setImageResource(R.drawable.badge_not_passed);
                    break;
            }


            return view;
        }
    }
}


