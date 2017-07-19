package com.example.aki.javaq.Presentation.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.aki.javaq.Presentation.SingleFragmentActivity;
import com.example.aki.javaq.R;


import java.util.UUID;

public class CommunityDetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CommunityDetailFragment();
    }

    public static final String EXTRA_POST_ID = "extra_crime_id";

    public static Intent newIntent(Context context, UUID postId) {
        Intent intent = new Intent(context, CommunityDetailActivity.class);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }
}
