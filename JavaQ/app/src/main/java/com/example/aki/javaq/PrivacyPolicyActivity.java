package com.example.aki.javaq;

import android.support.v4.app.Fragment;

/**
 * Created by MinaFujisawa on 2017/07/13.
 */

public class PrivacyPolicyActivity extends DrawermenuSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PrivacyPolicyFragment();
    }
}
