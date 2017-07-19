package com.example.aki.javaq.Presentation.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.aki.javaq.R;


import java.util.UUID;

public class CommunityDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POST_ID = "extra_crime_id";


    public static Intent newIntent(Context context, UUID postId) {
        Intent intent = new Intent(context, CommunityDetailActivity.class);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setContentView(R.layout.com_detail_activity);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.com_detail_fragment_container);


        if (fragment == null) {
            fragment = new CommunityDetailFragment();
            fm.beginTransaction().add(R.id.com_detail_fragment_container, fragment).commit();
        }
    }

}
