package com.example.aki.javaq;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultFragment extends Fragment{
    public static final String EXTRA_SCORE = "com.example.aki.javaq.score";
    private int mScore;
    private TextView mScoreTextView;
    private TextView mScoreCommentTextView;
    private ImageView mScoreBadge;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Intent intent = getActivity().getIntent();
        mScore = intent.getIntExtra(EXTRA_SCORE, 0);
        View v;
        if(mScore <= 5){
            v = inflater.inflate(R.layout.result_failed_fragment, container, false);
            mScoreTextView = (TextView) v.findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
        }
        else{
            v = inflater.inflate(R.layout.result_badge_fragment, container, false);
            mScoreTextView = (TextView) v.findViewById(R.id.result_score);
            mScoreTextView.setText(String.valueOf(mScore));
            mScoreCommentTextView = (TextView) v.findViewById(R.id.result_comment);
            mScoreBadge = (ImageView) v.findViewById(R.id.result_badge);
            if(mScore == 8){
                mScoreCommentTextView.setText("Fantastic!");
                mScoreBadge.setImageResource(R.drawable.badge_gold);
            }
            else if(mScore == 7){
                mScoreCommentTextView.setText("Great!");
                mScoreBadge.setImageResource(R.drawable.badge_silver);
            }
            else{
                mScoreCommentTextView.setText("Good!");
                mScoreBadge.setImageResource(R.drawable.badge_copper);
            }
        }


        return v;
    }

}

