package com.example.aki.javaq.Presentation.Community;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.aki.javaq.Domain.Entity.PostMain;
import com.example.aki.javaq.Domain.Entity.PostMainContents;
import com.example.aki.javaq.Domain.Helper.FirebaseNodes;
import com.example.aki.javaq.Domain.Helper.TimeUtils;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.aki.javaq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityListFragment extends Fragment {

    private static final String TAG = "CommunityListFragment";
    private View view;
    private RecyclerView mComRecyclerView;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FloatingActionButton mNewPostButton;
    public DatabaseReference mFirebaseDatabaseReference;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_CODE_LOGIN = 1;
    private static final String LOGIN_DIALOG = "login_dialog";
    private FirebaseRecyclerAdapter<PostMain, PostViewHolder> mFirebaseAdapter;

    private static int mLastAdapterClickedPosition = -1;
    private PostMain mPostMain;
    private String mUsername;
    private String mPostBody;
    private Uri mPhotoUrl;
    private String mPostTimeAgo;
    private String mPostKey;


    private int mCommentsNumInt = 18; //ダミー


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.com_list_fragment, container, false);
//        Intent intent = getActivity().getIntent();
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        mComRecyclerView = (RecyclerView) view.findViewById(R.id.com_list_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);


        //get user info
        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        mFirebaseUser = FirebaseLab.getFirebaseUser();
        if (mFirebaseUser != null) {
            mUsername = mFirebaseUser.getDisplayName();
            mPhotoUrl = mFirebaseUser.getPhotoUrl();
        }

        mFirebaseDatabaseReference = FirebaseLab.getFirebaseDatabaseReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<PostMain, PostViewHolder>(
                PostMain.class,
                R.layout.com_list_item,
                PostViewHolder.class,
                mFirebaseDatabaseReference.child(FirebaseNodes.PostMain.POSTS_CHILD)) {
            protected void populateViewHolder(final PostViewHolder viewHolder, PostMain PostMain, int position) {
                mPostMain = PostMain;
                mPostKey = this.getRef(position).getKey();
                mFirebaseDatabaseReference.child(FirebaseNodes.PostMain.POSTS_CHILD).child(mPostKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Set values
                        mPostBody = dataSnapshot.child(FirebaseNodes.PostMain.POST_BODY).getValue().toString();
                        viewHolder.mPostBodyTextView.setText(mPostBody);
                        long timestamp = (long) dataSnapshot.child(FirebaseNodes.PostMain.POST_TIME).getValue();
                        mPostTimeAgo = TimeUtils.getTimeAgo(timestamp);
                        viewHolder.mPostTimeTextView.setText(mPostTimeAgo);
                        String mCommentsNum = getResources().getQuantityString(R.plurals.comments_plural, mCommentsNumInt, mCommentsNumInt);
                        viewHolder.mCommentsNumTextView.setText(mCommentsNum);
                        viewHolder.mUserNameTextView.setText(mUsername);
                        Glide.with(getActivity())
                                .load(mPhotoUrl)
                                .into(viewHolder.mUserIconImageView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();
                        Intent intent = CommunityDetailActivity.newIntent(getActivity(), mPostKey);
//                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                        startActivity(intent);
                    }
                });
                return viewHolder;
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int postContentCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (postContentCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mComRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mComRecyclerView.setLayoutManager(mLinearLayoutManager);
        mComRecyclerView.setAdapter(mFirebaseAdapter);

        //For the issue floating action button unexpected anchor gravity change
        mComRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mComRecyclerView.removeOnLayoutChangeListener(this);

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mNewPostButton.getLayoutParams();
                lp.anchorGravity = Gravity.BOTTOM | GravityCompat.END;
                lp.setMargins(0, 0, 32, 32);
                mNewPostButton.setLayoutParams(lp);
            }
        });


        // FloatingActionButton
        mNewPostButton = (FloatingActionButton) view.findViewById(R.id.new_post_button);
        mNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFirebaseUser == null) {
                    // Not signed in, launch the Sign In activity
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    LoginDialogFragment dialog = LoginDialogFragment.newInstance(CommunityListFragment.this, REQUEST_CODE_LOGIN);
                    dialog.show(manager, LOGIN_DIALOG);

                } else {
                    Intent intent = new Intent(getActivity(), CommunityPostActivity.class);
                    startActivity(intent);
                }
            }
        });


        return view;
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder{
        //implements View.OnClickListener
        private TextView mUserNameTextView;
        private TextView mPostBodyTextView;
        private TextView mPostTimeTextView;
        private TextView mCommentsNumTextView;
        private CircleImageView mUserIconImageView;
        private PostViewHolder.ClickListener mClickListener;



        public PostViewHolder(View itemView) {
            super(itemView);

            mUserNameTextView = (TextView) itemView.findViewById(R.id.post_user_name);
            mPostBodyTextView = (TextView) itemView.findViewById(R.id.post_text);
            mPostTimeTextView = (TextView) itemView.findViewById(R.id.post_date);
            mCommentsNumTextView = (TextView) itemView.findViewById(R.id.post_comment_num);
            mUserIconImageView = (CircleImageView) itemView.findViewById(R.id.post_user_icon);

            //listener set on ENTIRE ROW, you may set on individual components within a row.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());

                }
            });

        }

        //Interface to send callbacks...
        public interface ClickListener {
            public void onItemClick(View view, int position);
        }

        public void setOnClickListener(PostViewHolder.ClickListener clickListener) {
            mClickListener = clickListener;
        }


    }


}