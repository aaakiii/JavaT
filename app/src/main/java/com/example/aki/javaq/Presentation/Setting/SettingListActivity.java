package com.example.aki.javaq.Presentation.Setting;


import android.support.v4.app.Fragment;

import com.example.aki.javaq.Presentation.DrawermenuSingleFragmentActivity;


public class SettingListActivity extends DrawermenuSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SettingListFragment();
    }
}
