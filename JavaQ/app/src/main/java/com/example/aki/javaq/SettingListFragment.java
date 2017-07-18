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

import com.example.aki.javaq.Community.CommunityPostActivity;
import com.example.aki.javaq.Community.LoginDialogFragment;
import com.example.aki.javaq.Quiz.Badge;
import com.example.aki.javaq.Quiz.QuizActivity;
import com.example.aki.javaq.Quiz.QuizFragment;
import com.example.aki.javaq.Quiz.QuizResultFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class SettingListFragment extends Fragment {
    ListView mListView;
    List<String> mSettingArrayList;
    String[] mSettingList;
    TextView mListTextView;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_list_fragment, container, false);

        mSettingArrayList = new ArrayList<>();
        mSettingList = getResources().getStringArray(R.array.setting_list);

        for (int i = 0; i < mSettingList.length; i++) {
            mSettingArrayList.add(mSettingList[i]);
        }

        myArrayAdapter adapter =
                new myArrayAdapter(getActivity(), R.layout.setting_list_item, mSettingArrayList);

        mListView = (ListView) view.findViewById(R.id.list_view_container);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //TODO: 未ログインだったらダイアログ表示
                        startActivity(new Intent(getActivity(), UserRegistrationActivity.class));
                        break;
                    case 1:
                        //TODO: notificationに設定
                        startActivity(new Intent(getActivity(), UserRegistrationActivity.class));
                        break;
                    case 2:
                        //TODO: log-outに設定
                        mFirebaseAuth = FirebaseAuth.getInstance();
                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        if(mFirebaseUser != null){
                            LoginDialogFragment.signOut();
                            Toast.makeText(getActivity(), "Sign out completed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "You're already signed out", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    default:
                        startActivity(new Intent(getActivity(), UserRegistrationActivity.class));
                        break;
                }
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
            mListTextView = (TextView) view.findViewById(R.id.list_text);
            mListTextView.setText(item);

            return view;
        }
    }


}


