package com.example.aki.javaq.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        setContentView(R.layout.com_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.com_detail_fragment_container);


        if (fragment == null) {
            fragment = new CommunityDetailFragment();
            fm.beginTransaction().add(R.id.com_detail_fragment_container, fragment).commit();
        }
    }

}
