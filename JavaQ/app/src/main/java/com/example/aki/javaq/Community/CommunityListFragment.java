package com.example.aki.javaq.Community;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.aki.javaq.LoginDialogFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.example.aki.javaq.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A placeholder fragment containing a simple view.
 */
public class CommunityListFragment extends Fragment {

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        //implements View.OnClickListener
        private TextView mPostUserName;
        private TextView mPostText;
        private TextView mPostDate;
        private TextView mCommentsNum;
        public static CircleImageView mUserIcon;
//        private Post mPost;

        public PostViewHolder(View v) {
            super(v);

//            itemView.setOnClickListener(this);
            mPostUserName = (TextView) itemView.findViewById(R.id.post_user_name);
            mPostText = (TextView) itemView.findViewById(R.id.post_text);
            mPostDate = (TextView) itemView.findViewById(R.id.post_date);
            mCommentsNum = (TextView) itemView.findViewById(R.id.post_comment_num);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.post_user_icon);


        }
    }


    private static final String TAG = "CommunityListFragment";
    private View view;
    private RecyclerView mComRecyclerView;
    private int mLastAdapterClickedPosition = -1;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FloatingActionButton mNewPostButton;
    public static final String MESSAGES_CHILD = "messages";
    public DatabaseReference mFirebaseDatabaseReference;
    private LinearLayoutManager mLinearLayoutManager;
    private String mPhotoUrl;
    private static final int REQUEST_CODE_LOGIN = 1;
    private static final String LOGIN_DIALOG = "login_dialog";

    private String mUsername;
    private FirebaseRecyclerAdapter<PostMainContents, PostViewHolder> mFirebaseAdapter;


    private int mCommentsNumInt = 18; //ダミー


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.com_list_fragment, container, false);
        Intent intent = getActivity().getIntent();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(getActivity(), GoogleSignInActivity.class));

        } else {
            mUsername = mFirebaseUser.getDisplayName();

            if (mFirebaseUser.getPhotoUrl() != null) {

            }

        }

        mComRecyclerView = (RecyclerView) view.findViewById(R.id.com_list_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);
//        if (savedInstanceState != null) {
//            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
//        }


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<PostMainContents, PostViewHolder>(
                PostMainContents.class,
                R.layout.com_list_item,
                PostViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)) {

            @Override
            protected PostMainContents parseSnapshot(DataSnapshot snapshot) {
                PostMainContents postMainContents = super.parseSnapshot(snapshot);
                if (postMainContents != null) {
                    postMainContents.setId(snapshot.getKey());
                }
                return postMainContents;
            }

            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder,
                                              PostMainContents postMainContents, int position) {
                if (postMainContents.getText() != null) {
                    viewHolder.mPostText.setText(postMainContents.getText());
                    viewHolder.mPostText.setVisibility(TextView.VISIBLE);
//                    viewHolder.m.setVisibility(ImageView.GONE);
                } else {
                    String imageUrl = postMainContents.getImageUrl();
                    if (imageUrl.startsWith("gs://")) {
                        StorageReference storageReference = FirebaseStorage.getInstance()
                                .getReferenceFromUrl(imageUrl);
                        storageReference.getDownloadUrl().addOnCompleteListener(
                                new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            String downloadUrl = task.getResult().toString();
//                                            Glide.with(viewHolder.messageImageView.getContext())
//                                                    .load(downloadUrl)
//                                                    .into(viewHolder.messageImageView);
                                        } else {
                                            Log.w(TAG, "Getting download url was not successful.",
                                                    task.getException());
                                        }
                                    }
                                });
                    } else {
//                        Glide.with(viewHolder.messageImageView.getContext())
//                                .load(friendlyMessage.getImageUrl())
//                                .into(viewHolder.messageImageView);
                    }
//                    viewHolder.messageImageView.setVisibility(ImageView.VISIBLE);
                    viewHolder.mPostText.setVisibility(TextView.GONE);
                }
                viewHolder.mPostUserName.setText(postMainContents.getName());
                if (postMainContents.getImageUrl() == null) {
//                    viewHolder.mUserIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),
//                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(getActivity())
                            .load(postMainContents.getImageUrl())
                            .into(viewHolder.mUserIcon);
                }
                if (postMainContents.getText() != null) {
                    // write this message to the on-device index
                    FirebaseAppIndex.getInstance().update(CommunityPostActivity.getMeesageIndexable(postMainContents));
                }

                // log a view action on it
                FirebaseUserActions.getInstance().end(CommunityPostActivity.getMessageViewAction(postMainContents));

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

        // FloatingActionButton
        mNewPostButton = (FloatingActionButton) view.findViewById(R.id.new_post_button);
        mNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // New Post画面へ遷移

                if (mFirebaseUser == null) {
                    // Not signed in, launch the Sign In activity
                    //TODO:DialogFragmentにするのか確認

//                    FragmentManager fm = getFragmentManager();
//                    LoginDialogFragment dialogFragment = LoginDialogFragment.newInstance();
                    startActivity(new Intent(getActivity(), LoginDialogFragment.class));

                } else {

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    LoginDialogFragment dialog = LoginDialogFragment.newInstance(CommunityListFragment.this, REQUEST_CODE_LOGIN);
                    dialog.show(manager, LOGIN_DIALOG);
//                    Intent intent = new Intent(getActivity(), CommunityPostActivity.class);
//                    startActivity(intent);

                }


            }
        });


        return view;
    }

}