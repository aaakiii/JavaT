package com.example.aki.javaq.Community;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aki.javaq.LoginDialogFragment;
import com.example.aki.javaq.R;
import com.example.aki.javaq.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommunityDetailFragment extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mUserNameTextView;
    private CircleImageView mUserIconImageView;
    private TextView mPostTextView;
    private TextView mPostDateTextView;
    private TextView mPostCommentsNumTextView;
    private EditText mAddCommentsEditTextView;
    private CircleImageView mMyIconImageView;
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mAdapter;
    private int mCommentsNumInt = 18; //ダミー
    private Date mCommentDate;
    private int mGoodNum;
    private int mBadNum;
    private boolean mGoodTapped;
    private boolean mBadTapped;

    private static final int REQUEST_CODE_LOGIN = 1;
    public static final String ARG_POST_ID = "arg_post_id";
    private static final String LOGIN_DIALOG = "login_dialog";


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

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // 3秒待機
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 3000);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.com_detail_fragment, container, false);
        mCommentsRecyclerView = (RecyclerView) view.findViewById(R.id.com_comments_recycler_view);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //For Refresh
        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));

        //For Post
        mUserNameTextView = (TextView) view.findViewById(R.id.post_user_name);
        mUserIconImageView = (CircleImageView) view.findViewById(R.id.post_user_icon);
        mPostTextView = (TextView) view.findViewById(R.id.post_text);
        mPostDateTextView = (TextView) view.findViewById(R.id.post_date);
        mPostCommentsNumTextView = (TextView) view.findViewById(R.id.post_comment_num);
        mUserNameTextView.setText("Post Name");
        mPostTextView.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic");
        mPostDateTextView.setText("5h");

        //For Add a comment
        mMyIconImageView = (CircleImageView) view.findViewById(R.id.my_user_icon);
        mAddCommentsEditTextView = (EditText) view.findViewById(R.id.add_new_comment_text);
        mAddCommentsEditTextView.setFocusable(false);
        mAddCommentsEditTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: ログイン済みかGET
                boolean mLogined = true;
                if (mLogined) {
                    Intent intent = new Intent(getActivity().getApplicationContext(),CommunityAddCommentActivity.class);
                    startActivity(intent);
                } else {
                    // display dialog
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    LoginDialogFragment dialog = LoginDialogFragment.newInstance(CommunityDetailFragment.this, REQUEST_CODE_LOGIN);
                    dialog.show(manager, LOGIN_DIALOG);
                }
            }
        });

        String comments = getResources().getQuantityString(R.plurals.comments_plural, mCommentsNumInt, mCommentsNumInt);
        mPostCommentsNumTextView.setText(comments);

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
        mAdapter = new CommentsAdapter(comments);
        mCommentsRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private class CommentsHolder extends RecyclerView.ViewHolder {
        private TextView mCommentUserNameTextView;
        private CircleImageView mCommentUserIconImageView;
        private TextView mCommentTextView;
        private TextView mCommentDateTextView;
        private ImageButton mCommentGoodButton;
        private ImageButton mCommentBadButton;
        private TextView mCommentGoodTextView;
        private TextView mCommentBadTextView;


//        private Post mPost;

        public CommentsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.com_detail_comment_item, parent, false));

            mCommentUserNameTextView = (TextView) itemView.findViewById(R.id.comment_user_name);
            mCommentUserIconImageView = (CircleImageView) itemView.findViewById(R.id.comment_user_icon);
            mCommentTextView = (TextView) itemView.findViewById(R.id.comment_text);
            mCommentDateTextView = (TextView) itemView.findViewById(R.id.comment_date);
            mCommentGoodTextView = (TextView) itemView.findViewById(R.id.comment_good_num);
            mCommentBadTextView = (TextView) itemView.findViewById(R.id.comment_bad_num);
            mCommentGoodButton = (ImageButton) itemView.findViewById(R.id.comment_button_good);
            mCommentBadButton = (ImageButton) itemView.findViewById(R.id.comment_button_bad);

            // TODO: データベースから取得
            mGoodNum = 12;
            mBadNum = 3;
            mCommentDate = new Date(2017 - 1900, 6, 10, 22, 49, 00);
            mGoodTapped = false;
            mBadTapped = false;
        }

        public void bind() {
//            mPost = post;

            //全部ダミー
            mCommentUserNameTextView.setText("getCommentUserName");
            mCommentTextView.setText("getCommentText");
            mCommentDateTextView.setText(TimeUtils.getTimeAgo(mCommentDate.getTime()));


            // Good button
            mCommentGoodButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addGood();
                }
            });
            mCommentGoodTextView.setText(String.valueOf(mGoodNum));
            mCommentGoodTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addGood();
                }
            });

            //Bad button
            mCommentBadButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addBad();
                }
            });

            mCommentBadTextView.setText(String.valueOf(mBadNum));
            mCommentBadTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addBad();
                }
            });

        }

        //TODO:mGoodNumとmGoodTappedをデータベースに保存
        private void addGood() {
            if (!mGoodTapped) {
                mGoodNum++;
                mCommentGoodTextView.setText(String.valueOf(mGoodNum));
                DrawableCompat.setTint(mCommentGoodButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_color));
                mGoodTapped = true;
            } else {
                mGoodNum--;
                mCommentGoodTextView.setText(String.valueOf(mGoodNum));
                DrawableCompat.setTint(mCommentGoodButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_text));
                mGoodTapped = false;
            }
        }

        private void addBad() {
            if (!mBadTapped) {
                mBadNum++;
                mCommentBadTextView.setText(String.valueOf(mBadNum));
                DrawableCompat.setTint(mCommentBadButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_color));
                mBadTapped = true;
            } else {
                mBadNum--;
                mCommentBadTextView.setText(String.valueOf(mBadNum));
                DrawableCompat.setTint(mCommentBadButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_text));
                mBadTapped = false;
            }
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