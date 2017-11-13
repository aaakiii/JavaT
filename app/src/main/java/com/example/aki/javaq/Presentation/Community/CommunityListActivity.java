package com.example.aki.javaq.Presentation.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.aki.javaq.Presentation.DrawerMenuActivity;
import com.example.aki.javaq.Presentation.DrawermenuSingleFragmentActivity;
import com.example.aki.javaq.Presentation.Setting.SettingListFragment;
import com.example.aki.javaq.R;

public class CommunityListActivity extends DrawermenuSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CommunityListFragment();
    }
}
