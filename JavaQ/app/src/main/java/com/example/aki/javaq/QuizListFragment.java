package com.example.aki.javaq;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class QuizListFragment extends Fragment {
    ListView mQuizListListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_list_fragment, container, false);

        List<String> mSectionList = new ArrayList<>();

        mSectionList.add("Basic concepts");
        mSectionList.add("Variable");
        mSectionList.add("Booleans");
        mSectionList.add("If and else");
        mSectionList.add("Arrays");
        mSectionList.add("String");
        mSectionList.add("ArrayLists");
        mSectionList.add("Loop");
        mSectionList.add("Method");
        mSectionList.add("Classes");

        // adapterのインスタンスを作成
        myArrayAdapter adapter =
                new myArrayAdapter(getActivity(), R.layout.quiz_list_item, mSectionList);

        mQuizListListView = (ListView) view.findViewById(R.id.quiz_list_view);
        mQuizListListView.setAdapter(adapter);

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
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            TextView mQuizListNameTextView = (TextView)view.findViewById(R.id.list_name);
            mQuizListNameTextView.setText(item);

            TextView mQuizListNumTextView = (TextView)view.findViewById(R.id.list_num);
            mQuizListNumTextView.setText("Quiz" + position);

            // アイコンをセット
//            ImageView appInfoImage = (ImageView)view.findViewById(R.id.item_image);
//            appInfoImage.setImageResource(item.getImageId());

            return view;
        }
    }
}


