package com.example.aki.javaq;


import android.support.v4.app.Fragment;

import com.example.aki.javaq.Progress.ProgressFragment;

public class SettingListActivity extends DrawermenuSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SettingListFragment();
    }
}
