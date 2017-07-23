package com.example.aki.javaq.Presentation.Community;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.aki.javaq.Domain.Entity.PostMain;
import com.example.aki.javaq.Domain.Entity.User;
import com.example.aki.javaq.Domain.Helper.FirebaseNodes;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;

import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aki.javaq.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityListFragment extends Fragment {

    private static final String TAG = "CommunityListFragment";
    private View view;
    private RecyclerView mComRecyclerView;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FloatingActionButton mNewPostButton;
    public DatabaseReference mPostsRef;
    public DatabaseReference mUsersRef;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_CODE_LOGIN = 1;
    private static final String LOGIN_DIALOG = "login_dialog";
    //    private FirebaseRecyclerAdapter<PostMain, PostViewHolder> mFirebaseAdapter;
    private PostAdapter mPostAdapter;

    private static int mLastAdapterClickedPosition = -1;
    private PostMain mPostMain;
    private String mUsername;
    private String mPostBody;
    private Uri mPhotoUrl;
    private String mPostTimeAgo;
    private String mPostKey;
    private String mUserId;


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


        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mComRecyclerView = (RecyclerView) view.findViewById(R.id.com_list_recycler_view);
        mComRecyclerView.setLayoutManager(mLinearLayoutManager);

        mPostsRef = FirebaseLab.getFirebaseDatabaseReference().child(FirebaseNodes.PostMain.POSTS_CHILD);
        mUsersRef = FirebaseLab.getFirebaseDatabaseReference().child(FirebaseNodes.User.USER_CHILD);

        if (mPostAdapter == null) {
            mPostAdapter = new PostAdapter(mPostsRef, mUsersRef);
            mComRecyclerView.setAdapter(mPostAdapter);
        }

        //get user info
        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        mFirebaseUser = FirebaseLab.getFirebaseUser();


//
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

    public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
        ArrayList<PostMain> mPostMainList = new ArrayList<>();
        ArrayList<User> mUserList = new ArrayList<>();

        public PostAdapter(DatabaseReference post_ref, DatabaseReference user_ref) {
            post_ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    mPostMainList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        PostMain mPostMain = postSnapshot.getValue(PostMain.class);
                        mPostMainList.add(mPostMain);
                    }
                    notifyDataSetChanged();
                }

                public void onCancelled(DatabaseError firebaseError) {
                }
            });

            user_ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    mUserList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        String key = user.getmUid();
                        mUserList.add(user);
                    }
                    notifyDataSetChanged();
                }

                public void onCancelled(DatabaseError firebaseError) {
                }
            });
        }

        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.com_list_item, parent, false);
            return new PostViewHolder(view);
        }


        public void onBindViewHolder(PostViewHolder viewHolder, int position) {
            PostMain mPostMain = mPostMainList.get(position);
            viewHolder.mPostBodyTextView.setText(mPostMain.getmPostBody());
            for (int i = 0; i < mUserList.size(); i++) {
                User mUser = mUserList.get(i);
                if (mPostMain.getmUserId().equals(mUser.getmUid())) {
                    viewHolder.mUserNameTextView.setText(mUser.getmUserName());
                }
            }
        }

        public int getItemCount() {
            return mPostMainList.size();
        }
    }

    ;

    public class PostViewHolder extends RecyclerView.ViewHolder {
        //implements View.OnClickListener
        private TextView mUserNameTextView;
        private TextView mPostBodyTextView;
        private TextView mPostTimeTextView;
        private TextView mCommentsNumTextView;
        private CircleImageView mUserIconImageView;
//        private PostViewHolder.ClickListener mClickListener;


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
//                    mClickListener.onItemClick(v, getAdapterPosition());

                }
            });

        }
//
//            //Interface to send callbacks...
//            public interface ClickListener {
//                public void onItemClick(View view, int position);
//            }
//
//            public void setOnClickListener(PostViewHolder.ClickListener clickListener) {
//                mClickListener = clickListener;
//            }


    }


}