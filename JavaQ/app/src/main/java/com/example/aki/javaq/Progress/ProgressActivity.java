package com.example.aki.javaq.Progress;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.aki.javaq.Community.CommunityListFragment;
import com.example.aki.javaq.DrawermenuSingleFragmentActivity;
import com.example.aki.javaq.R;

public class ProgressActivity extends DrawermenuSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProgressFragment();
    }
}
