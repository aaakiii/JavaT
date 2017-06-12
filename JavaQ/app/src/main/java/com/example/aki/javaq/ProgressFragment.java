package com.example.aki.javaq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by MinaFujisawa on 2017/06/06.
 */

public class ProgressFragment extends Fragment {
    ListView mListView;
    public static final String EXTRA_SECTION_POSITON = "aki.javaq_extra_section_position";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment, container, false);
        return view;
    }



}


