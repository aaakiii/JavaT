package com.example.aki.javaq.Community;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aki.javaq.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommunityDetailActivityFragment extends Fragment {

    public CommunityDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community_detail, container, false);
    }
}
