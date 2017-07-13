package com.example.aki.javaq.Community;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aki.javaq.R;

import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommunityDetailFragment extends Fragment {
    public static final String ARG_POST_ID = "arg_post_id";

    public static CommunityDetailFragment newInstance(UUID postID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST_ID, postID);
        CommunityDetailFragment fragment = new CommunityDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
//            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.com_detail_fragment, container, false);
//        mComRecyclerView = (RecyclerView) view.findViewById(R.id.com_list_recycler_view);
//        mComRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
