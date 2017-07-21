package com.example.aki.javaq.Presentation.Setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.example.aki.javaq.Domain.Usecase.SignInLab;
import com.example.aki.javaq.Presentation.Community.LoginDialogFragment;
import com.example.aki.javaq.R;
import com.example.aki.javaq.Presentation.UserRegistrationActivity;
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

        //TODO:未ログインだったら.length -1（logout非表示）
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
                        Intent intent = new Intent(getContext(), UserRegistrationActivity.class);
                        intent.putExtra(UserRegistrationActivity.NEW_USER, false);
                        startActivity(intent);
                        break;
                    case 1:
                        //TODO: notificationに設定
                        startActivity(new Intent(getActivity(), UserRegistrationActivity.class));
                        break;
                    case 2:
                        //TODO:showDialogを有効にする
//                        showDialog(getActivity());
                        mFirebaseUser = FirebaseLab.getFirebaseUser();
                        if(mFirebaseUser != null){
                            SignInLab.signOut();
                            Toast.makeText(getActivity(), "Sign out", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "already ..", Toast.LENGTH_SHORT).show();
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

    private void showDialog(Context context){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }

        builder.setMessage("Are you sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SignInLab.signOut();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }




}


