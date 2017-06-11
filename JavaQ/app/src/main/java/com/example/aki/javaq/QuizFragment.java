package com.example.aki.javaq;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class QuizFragment extends Fragment {
    private ImageView mProgressBar1;
    private ImageView mProgressBar2;
    private ImageView mProgressBar3;
    private ImageView mProgressBar4;
    private ImageView mProgressBar5;
    private ImageView mProgressBar6;
    private ImageView mProgressBar7;
    private ImageView mProgressBar8;
    private TextView mQuizText;
    private Button mFirstButtons;
    private Button mSecondButtons;
    private Button mThirdButtons;
    private int mCurrentIndex = 0;
    private Button mContinueButton;
    private int score = 0;
    private static final String KEY_INDEX = "index";
    private SoundPool soundPool;
    private int good_se;
    private int bad_se;
    private QuizLab quizLab;
    private Quiz mQuiz;
    final List<Quiz> mQuizzes = new QuizLab().getQuizzes();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        quizLab = new QuizLab();
        mQuiz = quizLab.getQuiz();
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        mProgressBar1 = (ImageView)v.findViewById(R.id.progress_bar_1);
        mProgressBar1.setImageResource(R.drawable.icon_progress_maincolor);
        mProgressBar2 = (ImageView)v.findViewById(R.id.progress_bar_2);
        mProgressBar3 = (ImageView)v.findViewById(R.id.progress_bar_3);
        mProgressBar4 = (ImageView)v.findViewById(R.id.progress_bar_4);
        mProgressBar5 = (ImageView)v.findViewById(R.id.progress_bar_5);
        mProgressBar6 = (ImageView)v.findViewById(R.id.progress_bar_6);
        mProgressBar7 = (ImageView)v.findViewById(R.id.progress_bar_7);
        mProgressBar8 = (ImageView)v.findViewById(R.id.progress_bar_8);
        mQuizText = (TextView) v.findViewById(R.id.question_item);
        mQuizText.setText(mQuizzes.get(mCurrentIndex).getmQuestionText());
        mFirstButtons = (Button) v.findViewById(R.id.first_button);
        mFirstButtons.setText(mQuizzes.get(mCurrentIndex).getmFirstChoice());
        mFirstButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mFirstButtons.setBackgroundResource(R.color.sub_color);
                mFirstButtons.setTextColor(getResources().getColor(R.color.white));
                if(checkAnswer(1) == false){
                    if(mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 2){
                        mSecondButtons.setBackgroundResource(R.color.white);
                        mSecondButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if(mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 3){
                        mThirdButtons.setBackgroundResource(R.color.white);
                        mThirdButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });
        mSecondButtons= (Button) v.findViewById(R.id.second_button);
        mSecondButtons.setText(mQuizzes.get(mCurrentIndex).getmSecondChoice());
        mSecondButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mSecondButtons.setBackgroundResource(R.color.sub_color);
                mSecondButtons.setTextColor(getResources().getColor(R.color.white));
                if(checkAnswer(2) == false){
                    if(mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 1){
                        mFirstButtons.setBackgroundResource(R.color.white);
                        mFirstButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if(mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 3){
                        mThirdButtons.setBackgroundResource(R.color.white);
                        mThirdButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }

            }
        });
        mThirdButtons = (Button) v.findViewById(R.id.third_button);
        mThirdButtons.setText(mQuizzes.get(mCurrentIndex).getmThirdChoice());
        mThirdButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mThirdButtons.setBackgroundResource(R.color.sub_color);
                mThirdButtons.setTextColor(getResources().getColor(R.color.white));
                if(checkAnswer(3) == false){
                    if(mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 1){
                        mFirstButtons.setBackgroundResource(R.color.white);
                        mFirstButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if(mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 2){
                        mSecondButtons.setBackgroundResource(R.color.white);
                        mSecondButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });
        mContinueButton = (Button) v.findViewById(R.id.continue_button);
        mContinueButton.setVisibility(INVISIBLE);
        mContinueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1);
                switch (mCurrentIndex){
                    case 1:
                        mProgressBar2.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                    case 2:
                        mProgressBar3.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                    case 3:
                        mProgressBar4.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                    case 4:
                        mProgressBar5.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                    case 5:
                        mProgressBar6.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                    case 6:
                        mProgressBar7.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                    case 7:
                        mProgressBar8.setImageResource(R.drawable.icon_progress_maincolor);
                        break;
                }
                if(mQuizzes.get(mCurrentIndex).getmQuizNumber() == 8){
                    Intent intent = ResultActivity.newIntent(getActivity(), score);
                    startActivity(intent);
                }
                else{
                    mCurrentIndex %= mQuizzes.size();
                    updateQuestion();
                }
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(attr).setMaxStreams(2).build();
        }
        good_se = soundPool.load(getContext(), R.raw.good, 1);
        bad_se = soundPool.load(getContext(), R.raw.bad, 1);

        return v;
    }
    public boolean checkAnswer(int mClickedAnswer){
        if(mClickedAnswer == mQuizzes.get(mCurrentIndex).getmAnswerIndex()){
            soundPool.play(good_se, 1F, 1F, 0, 0, 1F);
            mContinueButton.setVisibility(VISIBLE);
            score++;
            return true;
        }
        else{
            mContinueButton.setVisibility(VISIBLE);
            soundPool.play(bad_se, 1F, 1F, 0, 0, 1F);
            return false;
        }
    }
    public void updateQuestion(){

        mContinueButton.setVisibility(INVISIBLE);
        String question = mQuizzes.get(mCurrentIndex).getmQuestionText();
        mQuizText.setText(question);
        mFirstButtons.setBackgroundResource(R.color.white);
        mFirstButtons.setTextColor(getResources().getColor(R.color.main_text));
        String first_button = mQuizzes.get(mCurrentIndex).getmFirstChoice();
        mFirstButtons.setText(first_button);
        mSecondButtons.setBackgroundResource(R.color.white);
        mSecondButtons.setTextColor(getResources().getColor(R.color.main_text));
        String second_button = mQuizzes.get(mCurrentIndex).getmSecondChoice();
        mSecondButtons.setText(second_button);
        mThirdButtons.setBackgroundResource(R.color.white);
        mThirdButtons.setTextColor(getResources().getColor(R.color.main_text));
        String third_button = mQuizzes.get(mCurrentIndex).getmThirdChoice();
        mThirdButtons.setText(third_button);
    }
    @Override
    public void onPause() {
        super.onPause();
        soundPool.release();
    }


}
