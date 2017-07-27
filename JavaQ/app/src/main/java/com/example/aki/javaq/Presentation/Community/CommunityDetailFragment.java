package com.example.aki.javaq.Presentation.Community;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aki.javaq.Domain.Entity.PostComment;
import com.example.aki.javaq.Domain.Entity.PostMain;
import com.example.aki.javaq.Domain.Entity.User;
import com.example.aki.javaq.Domain.Helper.FirebaseNodes;
import com.example.aki.javaq.Domain.Usecase.FirebaseLab;
import com.example.aki.javaq.R;
import com.example.aki.javaq.Domain.Helper.TimeUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private CommentsAdapter mAdapter;
    private int mCommentsNumInt = 0;
    private Date mCommentDate;
    private int mGoodNum;
    private int mBadNum;
    private boolean mGoodTapped;
    private boolean mBadTapped;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String mPostKey;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    public DatabaseReference mCommentsRef;
    public DatabaseReference mUsersRef;
    private CommentsAdapter mCommentsAdapter;
    private RecyclerView mCommentsRecyclerView;
    private String mPostTimeAgo;
    private static final int REQUEST_CODE_LOGIN = 1;
    public static final String ARG_POST_KEY = "arg_post_key";
    private static final String LOGIN_DIALOG = "login_dialog";
    private View view;
    private LinearLayoutManager mLinearLayoutManager;
    public static final String POST_KEY = "post_key";
    private SharedPreferences mSharedPreferences;
    private static final String POST_SENT_EVENT = "post_sent";
    private String mCommentsNum;

    public static CommunityDetailFragment newInstance(String postKey) {
        Bundle args = new Bundle();
        args.putString(ARG_POST_KEY, postKey);
        CommunityDetailFragment fragment = new CommunityDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostKey = getArguments().getString(ARG_POST_KEY);
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
        view = inflater.inflate(R.layout.com_detail_fragment, container, false);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mCommentsRecyclerView = (RecyclerView) view.findViewById(R.id.com_comments_recycler_view);
        mCommentsRecyclerView.setLayoutManager(mLinearLayoutManager);
        //For Refresh
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
//        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
//        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));

        //For Post
        mUserNameTextView = (TextView) view.findViewById(R.id.post_user_name);
        mUserIconImageView = (CircleImageView) view.findViewById(R.id.post_user_icon);
        mPostTextView = (TextView) view.findViewById(R.id.post_text);
        mPostDateTextView = (TextView) view.findViewById(R.id.post_date);
        mPostCommentsNumTextView = (TextView) view.findViewById(R.id.post_comment_num);
        mFirebaseDatabaseReference = FirebaseLab.getFirebaseDatabaseReference();
        mCommentsRef = FirebaseLab.getFirebaseDatabaseReference().child(FirebaseNodes.PostComment.POSTS_COM_CHILD);
        mUsersRef = FirebaseLab.getFirebaseDatabaseReference().child(FirebaseNodes.User.USER_CHILD);

        mFirebaseUser = FirebaseLab.getFirebaseUser();
        mFirebaseDatabaseReference.child(FirebaseNodes.PostMain.POSTS_CHILD).child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mPostTextView.setText(dataSnapshot.child(FirebaseNodes.PostMain.POST_BODY).getValue().toString());

                mFirebaseDatabaseReference.child(FirebaseNodes.User.USER_CHILD).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot snapshot) {
                        mUserNameTextView.setText(snapshot.child(dataSnapshot.child(FirebaseNodes.PostMain.USER_ID).getValue().toString()).child(FirebaseNodes.User.USER_NAME).getValue().toString());
                        //Display User picture
                        StorageReference rootRef = FirebaseLab.getStorageReference().child(FirebaseNodes.UserPicture.USER_PIC_CHILD);
                        rootRef.child(dataSnapshot.child(FirebaseNodes.PostMain.USER_ID).getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //If there's a picture in the storage, set the picture
                                Glide.with(getActivity())
                                        .load(uri)
                                        .into(mUserIconImageView);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //If not, set the default picture
                                int id = R.drawable.image_user_default;
                                Uri mPictureDefaultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                        "://" + getResources().getResourcePackageName(id)
                                        + '/' + getResources().getResourceTypeName(id)
                                        + '/' + getResources().getResourceEntryName(id));
                                Glide.with(getActivity())
                                        .load(mPictureDefaultUri)
                                        .into(mUserIconImageView);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mPostDateTextView.setText(TimeUtils.getTimeAgo((long) dataSnapshot.child(FirebaseNodes.PostMain.POST_TIME).getValue()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        if (mCommentsAdapter == null) {
            mCommentsAdapter = new CommunityDetailFragment.CommentsAdapter(mCommentsRef, mUsersRef);
            mCommentsRecyclerView.setAdapter(mCommentsAdapter);
        }


        //get user info
        mFirebaseAuth = FirebaseLab.getFirebaseAuth();
        mFirebaseUser = FirebaseLab.getFirebaseUser();
        //For Add a comment
        mMyIconImageView = (CircleImageView) view.findViewById(R.id.my_user_icon);
        StorageReference rootRef = FirebaseLab.getStorageReference().child(FirebaseNodes.UserPicture.USER_PIC_CHILD);
        rootRef.child(mFirebaseUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //If there's a picture in the storage, set the picture
                Glide.with(getActivity())
                        .load(uri)
                        .into(mMyIconImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //If not, set the default picture
                int id = R.drawable.image_user_default;
                Uri mPictureDefaultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(id)
                        + '/' + getResources().getResourceTypeName(id)
                        + '/' + getResources().getResourceEntryName(id));
                Glide.with(getActivity())
                        .load(mPictureDefaultUri)
                        .into(mMyIconImageView);
            }
        });


        mAddCommentsEditTextView = (EditText) view.findViewById(R.id.add_new_comment_text);
        mAddCommentsEditTextView.setFocusable(false);
        mAddCommentsEditTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: ログイン済みかGET
                boolean mLogined = true;
                if (mLogined) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), CommunityAddCommentActivity.class);
                    intent.putExtra(POST_KEY, mPostKey);
                    startActivity(intent);
                } else {
                    // display dialog
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    LoginDialogFragment dialog = LoginDialogFragment.newInstance();
                    dialog.show(manager, LOGIN_DIALOG);
                }
            }
        });


            final DatabaseReference post_ref = mFirebaseDatabaseReference.child(FirebaseNodes.PostMain.POSTS_CHILD).child(mPostKey);
            post_ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    PostMain mPostMain = snapshot.getValue(PostMain.class);
                    mCommentsNumInt = mPostMain.getCommentNum();
                    mCommentsNum = getResources().getQuantityString(R.plurals.comments_plural, mCommentsNumInt, mCommentsNumInt);
                    mPostCommentsNumTextView.setText(mCommentsNum);
                }
                public void onCancelled(DatabaseError firebaseError) {
                }
            });
//        }

        return view;
    }


    public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {
        ArrayList<PostComment> mPostCommentsList = new ArrayList<>();
        HashMap<String, User> mUserMap = new HashMap<>();
        private PostComment mPostComment;

        public CommentsAdapter(DatabaseReference comment_ref, DatabaseReference user_ref) {
            comment_ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    mPostCommentsList.clear();
                    for (DataSnapshot commentsSnapshot : snapshot.getChildren()) {
                        PostComment mPostComment = commentsSnapshot.getValue(PostComment.class);
                        if (mPostComment.getPostId().equals(mPostKey)) {
                            mPostCommentsList.add(mPostComment);
                        }
                    }
                    notifyDataSetChanged();
                }

                public void onCancelled(DatabaseError firebaseError) {
                }
            });

            user_ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    mUserMap.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        String key = postSnapshot.getKey();
                        mUserMap.put(key, user);
                    }
                    notifyDataSetChanged();
                }

                public void onCancelled(DatabaseError firebaseError) {
                }
            });
        }

        @Override
        public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.com_detail_comment_item, parent, false);
            return new CommentsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CommentsViewHolder viewHolder, int position) {
            mPostComment = mPostCommentsList.get(position);
            viewHolder.bind(mPostComment);

            //Display Body text
            viewHolder.mCommentTextView.setText(mPostComment.getComBody());

            //Display Time
            long timestamp = mPostComment.getComTime();
            mPostTimeAgo = TimeUtils.getTimeAgo(timestamp);
            viewHolder.mCommentTimeTextView.setText(mPostTimeAgo);

            //Display good / bad num
            //TODO:numはローカルにする
            mGoodNum = mPostComment.getComLike();
            viewHolder.mCommentGoodTextView.setText(String.valueOf(mGoodNum));
            mBadNum = mPostComment.getComUnlike();
            viewHolder.mCommentBadTextView.setText(String.valueOf(mBadNum));

            // Good Bad button
            viewHolder.mCommentGoodButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DatabaseReference good_ref = mCommentsRef.child(mPostComment.getComId()).child(FirebaseNodes.PostComment.LIKE);

                    if (!mGoodTapped) {
                        if (mBadTapped) {
                            mBadNum--;
                            viewHolder.mCommentBadTextView.setText(String.valueOf(mBadNum));
                            DrawableCompat.setTint(viewHolder.mCommentBadButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_text));
                            mBadTapped = false;
                        }
                        mGoodNum++;
                        viewHolder.mCommentGoodTextView.setText(String.valueOf(mGoodNum));
                        DrawableCompat.setTint(viewHolder.mCommentGoodButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_color));
                        mGoodTapped = true;
//                        good_ref.setValue(mGoodNum);
                    } else {
                        mGoodNum--;
                        viewHolder.mCommentGoodTextView.setText(String.valueOf(mGoodNum));
                        DrawableCompat.setTint(viewHolder.mCommentGoodButton.getDrawable(), ContextCompat.getColor(getActivity(), R.color.sub_text));
                        mGoodTapped = false;
//                        good_ref.setValue(mGoodNum);
                    }
                }
            });
            viewHolder.mCommentGoodTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    addGood(mPostComment.getComId());
                }
            });


            if (mUserMap.containsKey(mPostComment.getUserId().toString())) {
                //Display User name
                User mUser = mUserMap.get(mPostComment.getUserId().toString());
                viewHolder.mCommentUserNameTextView.setText(mUser.getUserName());

                //Display User picture
                StorageReference rootRef = FirebaseLab.getStorageReference().child(FirebaseNodes.UserPicture.USER_PIC_CHILD);
                rootRef.child(mUser.getUserId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //If there's a picture in the storage, set the picture
                        Glide.with(getActivity())
                                .load(uri)
                                .into(viewHolder.mCommentUserIconImageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //If not, set the default picture
                        int id = R.drawable.image_user_default;
                        Uri mPictureDefaultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                "://" + getResources().getResourcePackageName(id)
                                + '/' + getResources().getResourceTypeName(id)
                                + '/' + getResources().getResourceEntryName(id));
                        Glide.with(getActivity())
                                .load(mPictureDefaultUri)
                                .into(viewHolder.mCommentUserIconImageView);
                    }
                });
            }


        }

        @Override
        public int getItemCount() {
            return mPostCommentsList.size();
        }

    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        private TextView mCommentUserNameTextView;
        private CircleImageView mCommentUserIconImageView;
        private TextView mCommentTextView;
        private TextView mCommentTimeTextView;
        private ImageButton mCommentGoodButton;
        private ImageButton mCommentBadButton;
        private TextView mCommentGoodTextView;
        private TextView mCommentBadTextView;
        private PostComment mPostComment;
        private User mUser;

        public CommentsViewHolder(View itemView) {
            super(itemView);

            mCommentUserNameTextView = (TextView) itemView.findViewById(R.id.comment_user_name);
            mCommentUserIconImageView = (CircleImageView) itemView.findViewById(R.id.comment_user_icon);
            mCommentTextView = (TextView) itemView.findViewById(R.id.comment_text);
            mCommentTimeTextView = (TextView) itemView.findViewById(R.id.comment_date);
            mCommentGoodTextView = (TextView) itemView.findViewById(R.id.comment_good_num);
            mCommentBadTextView = (TextView) itemView.findViewById(R.id.comment_bad_num);
            mCommentGoodButton = (ImageButton) itemView.findViewById(R.id.comment_button_good);
            mCommentBadButton = (ImageButton) itemView.findViewById(R.id.comment_button_bad);

//            // Good / Bad button
//            mCommentGoodButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    addGood(mPostComment.getComId());
//                }
//            });
//            mCommentGoodTextView.setText(String.valueOf(mGoodNum));
//            mCommentGoodTextView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    addGood(mPostComment.getComId());
//                }
//            });

//            //Bad button
//            mCommentBadButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
////                        addBad();
//                }
//            });
//            mCommentBadTextView.setText(String.valueOf(mBadNum));
//            mCommentBadTextView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
////                        addBad();
//                }
//            });

        }

        public void bind(final PostComment postComment) {
            mPostComment = postComment;
        }


    }


}