package com.example.aki.javaq.Presentation.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.Toast;

import com.example.aki.javaq.Domain.Entity.Quiz;
import com.example.aki.javaq.Domain.Usecase.QuizLab;
import com.example.aki.javaq.R;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class QuizFragment extends Fragment {

    private TextView mQuizTextView;
    private Button mFirstButton;
    private Button mSecondButton;
    private Button mThirdButton;
    private Button mFourthButton;
    private int mCurrentIndex = 0;
    private int mSelectedAnswer;
    private int mCorrectAnswerIndex;
    private Button mContinueButton;
    private int score = 0;
    private static final String KEY_INDEX = "index";
    private SoundPool soundPool;
    private int good_se;
    private int bad_se;
    private int mCurrentSectionID;
    private String[] mSectionList;
    private List<Quiz> mQuizzes;
    private String[] mCorrectWords;
    private String[] mIncorrectWords;
    private static final String EXTRA_SCORE = "com.example.aki.javaq.score";
    private static final String EXTRA_QUIZZES = "com.example.aki.javaq.quizzes";
    public static final String EXTRA_CURRENT_SECTION_ID = "com.example.aki.javaq.current_section_id";
    public static final String SHARED_PREF_SCORE_KEY = "JavaQ_keyword_score";
    public static final String SHARED_PREF_SCORE = "JavaQ_keyword_score";
    private LinearLayout mLinearLayout;
    private ImageView mPopUpImageView;
    private TextView mPopUpTextView;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.quiz_fragment, container, false);

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mCurrentSectionID = settings.getInt("position", 0);
        mQuizzes = new QuizLab(mCurrentSectionID).getQuizzes();
        mSectionList = getResources().getStringArray(R.array.section_list);
        mLinearLayout = (LinearLayout) v.findViewById(R.id.progress_linear);
        setProgressBar();
        Collections.shuffle(mQuizzes);

        //set view
        mQuizTextView = (TextView) v.findViewById(R.id.question_item);
        mQuizTextView.setText(mQuizzes.get(mCurrentIndex).getmQuestionText());
        mPopUpImageView = (ImageView) v.findViewById(R.id.answer_image_popup);
        mPopUpImageView.setVisibility(INVISIBLE);
        mPopUpTextView = (TextView) v.findViewById(R.id.answer_text_popup);
        mPopUpTextView.setVisibility(INVISIBLE);

        mFirstButton = (Button) v.findViewById(R.id.first_button);
        mFirstButton.setText(mQuizzes.get(mCurrentIndex).getmFirstChoice());
        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsEnable(false);
                setItemVisibility(true);
                checkAnswer(1);
                setButtonColor();
            }
        });

        mSecondButton = (Button) v.findViewById(R.id.second_button);
        mSecondButton.setText(mQuizzes.get(mCurrentIndex).getmSecondChoice());
        mSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsEnable(false);
                setItemVisibility(true);
                checkAnswer(2);
                setButtonColor();
            }
        });

        mThirdButton = (Button) v.findViewById(R.id.third_button);
        mThirdButton.setText(mQuizzes.get(mCurrentIndex).getmThirdChoice());
        mThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsEnable(false);
                setItemVisibility(true);
                checkAnswer(3);
                setButtonColor();
            }
        });

        mFourthButton = (Button) v.findViewById(R.id.fourth_button);
        mFourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsEnable(false);
                setItemVisibility(true);
                checkAnswer(4);
                setButtonColor();
            }
        });

        if(mQuizzes.get(mCurrentIndex).getmFourthChoice() != null){
            mFourthButton.setText(mQuizzes.get(mCurrentIndex).getmFourthChoice());
        }else {
            mFourthButton.setVisibility(INVISIBLE);
        }

        mContinueButton = (Button) v.findViewById(R.id.continue_button);
        mContinueButton.getBackground().setAlpha(128);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1);
                setItemVisibility(false);
                setButtonsEnable(true);
                mLinearLayout.removeAllViews();
                setProgressBar();
                if (mCurrentIndex == mQuizzes.size()) {
                    Intent intent = new Intent(getActivity().getApplication(), QuizResultActivity.class);
                    intent.putExtra(EXTRA_SCORE, score);
                    intent.putExtra(EXTRA_CURRENT_SECTION_ID, mCurrentSectionID);
                    intent.putExtra(EXTRA_QUIZZES, mQuizzes.size());
                    startActivity(intent);

                    //SharedPreferences
                    SharedPreferences data = getActivity().getSharedPreferences(SHARED_PREF_SCORE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putInt(mSectionList[mCurrentSectionID] + SHARED_PREF_SCORE_KEY, score);
                    editor.apply();

                } else {
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



    private boolean checkAnswer(int inputAnswer) {
        mSelectedAnswer = inputAnswer;
        mCorrectAnswerIndex = mQuizzes.get(mCurrentIndex).getmAnswerIndex();
        if (mSelectedAnswer == mCorrectAnswerIndex) {
            soundPool.play(good_se, 1F, 1F, 0, 0, 1F);
            mPopUpImageView.setImageResource(R.drawable.ic_correct);
            mCorrectWords = getResources().getStringArray(R.array.Correct_word_list);
            int correct = new Random().nextInt(mCorrectWords.length);
            String correctWord = (mCorrectWords[correct]);
            mPopUpTextView.setText(correctWord);
            mPopUpTextView.setTextColor(getResources().getColor(R.color.main_color));
            score++;
            return true;
        } else {
            mPopUpImageView.setImageResource(R.drawable.ic_incorrect);
            soundPool.play(bad_se, 1F, 1F, 0, 0, 1F);
            mIncorrectWords = getResources().getStringArray(R.array.Incorrect_word_list);
            int incorrect = new Random().nextInt(mIncorrectWords.length);
            String incorrectWord = (mIncorrectWords[incorrect]);
            mPopUpTextView.setText(incorrectWord);
            mPopUpTextView.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
    }

    private void setButtonColor(){
        Button mAnswerButton = null;

        //set correct color
        mAnswerButton = getButtonByIndex(mCorrectAnswerIndex);
        mAnswerButton.setBackgroundResource(R.drawable.collect_answer_button_customize);
        mAnswerButton.setTextColor(getResources().getColor(R.color.white));

        //set incorrect color
        if(mSelectedAnswer != mCorrectAnswerIndex){
            Button mInCorrectButton =  getButtonByIndex(mSelectedAnswer);
            mInCorrectButton.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
            mInCorrectButton.setTextColor(getResources().getColor(R.color.red));
        }
    }

    private Button getButtonByIndex(int index){
        Button btn = null;
        switch (index){
            case 1:
                btn = mFirstButton;
                break;
            case 2:
                btn = mSecondButton;
                break;
            case 3:
                btn = mThirdButton;
                break;
            case 4:
                btn = mFourthButton;
        }
        return btn;
    }


    private void setItemVisibility(boolean on) {
        if(on){
            mContinueButton.getBackground().setAlpha(255);
            mPopUpImageView.setVisibility(VISIBLE);
            mPopUpTextView.setVisibility(VISIBLE);
        }
        else{
            mPopUpImageView.setVisibility(INVISIBLE);
            mPopUpTextView.setVisibility(INVISIBLE);
        }
    }

    private void updateQuestion() {
        mContinueButton.getBackground().setAlpha(128);
        String question = mQuizzes.get(mCurrentIndex).getmQuestionText();
        mQuizTextView.setText(question);

        mFirstButton.setBackgroundResource(R.drawable.answer_button_customize);
        mFirstButton.setTextColor(getResources().getColor(R.color.main_text));
        String first_button = mQuizzes.get(mCurrentIndex).getmFirstChoice();
        mFirstButton.setText(first_button);

        mSecondButton.setBackgroundResource(R.drawable.answer_button_customize);
        mSecondButton.setTextColor(getResources().getColor(R.color.main_text));
        String second_button = mQuizzes.get(mCurrentIndex).getmSecondChoice();
        mSecondButton.setText(second_button);

        mThirdButton.setBackgroundResource(R.drawable.answer_button_customize);
        mThirdButton.setTextColor(getResources().getColor(R.color.main_text));
        String third_button = mQuizzes.get(mCurrentIndex).getmThirdChoice();
        mThirdButton.setText(third_button);

        mFourthButton.setBackgroundResource(R.drawable.answer_button_customize);
        mFourthButton.setTextColor(getResources().getColor(R.color.main_text));
        String fourth_button = mQuizzes.get(mCurrentIndex).getmFourthChoice();
        mFourthButton.setText(fourth_button);
        if(mQuizzes.get(mCurrentIndex).getmFourthChoice() != null) {
            mFourthButton.setVisibility(VISIBLE);
        }else{
            mFourthButton.setVisibility(INVISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        soundPool.release();
    }

    private void setButtonsEnable(boolean on) {
        if (on) {
            mFirstButton.setEnabled(true);
            mSecondButton.setEnabled(true);
            mThirdButton.setEnabled(true);
            mFourthButton.setEnabled(true);
        } else {
            mFirstButton.setEnabled(false);
            mSecondButton.setEnabled(false);
            mThirdButton.setEnabled(false);
            mFourthButton.setEnabled(false);
        }
    }

    private void setProgressBar(){
        GradientDrawable progress_circle;
        for (int i = 0; i < mQuizzes.size(); i++) {
            ImageView mProgressBar = new ImageView(getActivity().getApplicationContext());
            mProgressBar.setId(i);
            mProgressBar.setPadding(6, 0, 6, 0);
            progress_circle = (GradientDrawable) getResources().getDrawable(R.drawable.quiz_progress_circle);
            progress_circle.setColor(ContextCompat.getColor(getActivity(),R.color.light_gray));
            mProgressBar.setImageDrawable(progress_circle);

            //set width and height
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
            mProgressBar.setLayoutParams(layoutParams);

            //set green color
            if(i <= mCurrentIndex){
                progress_circle = (GradientDrawable) getResources().getDrawable(R.drawable.quiz_progress_circle);
                progress_circle.setColor(ContextCompat.getColor(getActivity(),R.color.main_color));
                mProgressBar.setImageDrawable(progress_circle);
            }
            mLinearLayout.addView(mProgressBar);
        }
    }
}
