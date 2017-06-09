package com.example.aki.javaq;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class QuizListActivity extends QuizListSingleFragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;

    private final String[] mMainMenuItems = new String[]{"Quiz", "Community", "Progress", "Nortifications", "Setting"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list_fragment_activity);

//        _txvMessage = (TextView) findViewById(R.id.txvMessage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drwMain);

        // ツールバーの一番左にトグルボタンを設定
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // ナビゲーションドロワの中にリストビューを定義
        mListView = (ListView) findViewById(R.id.lsvDrawer);
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMainMenuItems));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ナビゲーションドロワを左側に閉じる
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // アクティビティ作成後にトグルボタンを同期
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Android端末のステータスに変化があった場合、その内容をトグルボタンに伝える
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected Fragment createFragment() {
        return new QuizListFragment();
    }
}