package com.example.aki.javaq;


import android.support.v4.app.Fragment;


public class SettingListActivity extends DrawermenuSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SettingListFragment();
    }
}
