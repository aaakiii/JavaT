package com.example.aki.javaq.Community;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aki.javaq.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommunityListFragment extends Fragment {

    private RecyclerView mComRecyclerView;
    private CrimeAdapter mAdapter;


    public CommunityListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.com_list_fragment, container, false);
        mComRecyclerView = (RecyclerView) view.findViewById(R.id.com_list_recycler_view);
        mComRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }



    private class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mUserName;
        private TextView mPostText;
//        private Post mPost;

        public PostHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.com_list_item, parent, false));

            itemView.setOnClickListener(this);
            mUserName = (TextView) itemView.findViewById(R.id.post_user_name);
            mPostText = (TextView) itemView.findViewById(R.id.post_text);
        }

        @Override
        public void onClick(View v) {
//            mLastAdapterClickedPosition = getAdapterPosition();
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(intent);
        }

        public void bind() {
//            mPost = post;
            mUserName.setText("getUserName");
            mPostText.setText("getPostText");
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter<PostHolder> {
//        private List<Post> mPosts;
//
//        public CrimeAdapter(ArrayList<Post> posts) {
//            mPosts = posts;
//        }

        private ArrayList<String> dummyList;

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            createDummyList();
            return new PostHolder(layoutInflater, parent);
        }

        private void createDummyList(){
            dummyList.add("a");
            dummyList.add("b");
            dummyList.add("c");
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
//            Crime crime = mCrimes.get(position);
            holder.bind();
        }


        @Override
        public int getItemCount() {

//            return mCrimes.size();
            return 10;
        }

//        public void setCrimes(List<Crime> crimes) {
//            mCrimes = crimes;
//        }
    }
}
