package com.example.aki.javaq.Community;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aki.javaq.R;

import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommunityDetailFragment extends Fragment {
    private TextView mUserName;
    private CircleImageView mUserIcon;
    private TextView mPostText;
    private TextView mPostDate;
    private TextView mPostCommentsNum;
    private EditText mAddCommentsText;
    private CircleImageView mMyIcon;
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mAdapter;
    private int mCommentsNumInt = 18; //ダミー

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
        mCommentsRecyclerView = (RecyclerView) view.findViewById(R.id.com_comments_recycler_view);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //For Post
        mUserName = (TextView) view.findViewById(R.id.post_user_name);
        mUserIcon = (CircleImageView) view.findViewById(R.id.post_user_icon);
        mPostText = (TextView) view.findViewById(R.id.post_text);
        mPostDate = (TextView) view.findViewById(R.id.post_date);
        mPostCommentsNum = (TextView) view.findViewById(R.id.post_comment_num);
        mUserName.setText("Post Name");
        mPostText.setText("Post Test");
        mPostDate.setText("5h");

        //For Add a comment
        mMyIcon = (CircleImageView) view.findViewById(R.id.my_user_icon);
        mAddCommentsText = (EditText) view.findViewById(R.id.add_new_comment_text);
        mAddCommentsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //入力したテキストをセット
//                mComment.setCommentText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        String comments = getResources().getQuantityString(R.plurals.comments_plural, mCommentsNumInt, mCommentsNumInt);
        mPostCommentsNum.setText(comments);

        updateUI();
        return view;
    }

    private void updateUI() {

        // ダミーの配列
        ArrayList<String> comments = new ArrayList<>();
        comments.add("A");
        comments.add("B");
        comments.add("C");

//        CrimeLab crimeLab = CrimeLab.get(getActivity());

//        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CommentsAdapter(comments);
            mCommentsRecyclerView.setAdapter(mAdapter);
        } else {
//            if (mLastAdapterClickedPosition >= 0) {
//                mAdapter.notifyItemChanged(mLastAdapterClickedPosition);
//                mLastAdapterClickedPosition = -1;
//            }
//            mAdapter.setCrimes(crimes);
        }
        mAdapter.notifyDataSetChanged();
    }




    private class CommentsHolder extends RecyclerView.ViewHolder {
        private TextView mCommentUserName;
        private CircleImageView mCommentUserIcon;
        private TextView mCommentText;
        private TextView mCommentDate;
        private TextView mCommentGood;
        private TextView mCommentBad;

//        private Post mPost;

        public CommentsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.com_detail_comment_item, parent, false));

            mCommentUserName = (TextView) itemView.findViewById(R.id.comment_user_name);
            mCommentUserIcon = (CircleImageView) itemView.findViewById(R.id.comment_user_icon);
            mCommentText = (TextView) itemView.findViewById(R.id.comment_text);
            mCommentDate = (TextView) itemView.findViewById(R.id.comment_date);
            mCommentGood = (TextView) itemView.findViewById(R.id.comment_good_num);
            mCommentBad = (TextView) itemView.findViewById(R.id.comment_bad_num);
        }

        public void bind() {
//            mPost = post;

            //全部ダミー
            mCommentUserName.setText("getCommentUserName");
            mCommentText.setText("getCommentText");
            mCommentDate.setText("4m");
            mCommentGood.setText("12");
            mCommentBad.setText("2");
        }

    }

    private class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder> {
        private ArrayList<String> mComments;
        public CommentsAdapter(ArrayList<String> comments) {
            mComments = comments;
        }

        private ArrayList<String> dummyList;

        @Override
        public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CommentsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CommentsHolder holder, int position) {
//            Crime crime = mCrimes.get(position);
            holder.bind();
        }


        @Override
        public int getItemCount() {
            return mComments.size();
        }

//        public void setCrimes(List<Crime> crimes) {
//            mCrimes = crimes;
//        }
    }
}
