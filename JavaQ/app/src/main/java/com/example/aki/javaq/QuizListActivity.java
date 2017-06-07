package com.example.aki.javaq;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizListActivity extends QuizListSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new QuizListFragment();
    }

}
