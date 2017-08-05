package com.example.aki.javaq.Presentation.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.aki.javaq.Domain.Entity.Quiz;
import com.example.aki.javaq.Domain.Usecase.QuizLab;
import com.example.aki.javaq.R;

import java.util.List;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class QuizFragment extends Fragment {

    private TextView mQuizTextView;
    private Button mFirstButton;
    private Button mSecondButton;
    private Button mThirdButton;
    private int mCurrentIndex = 0;
    private Button mContinueButton;
    private int score = 0;
    private static final String KEY_INDEX = "index";
    private SoundPool soundPool;
    private int good_se;
    private int bad_se;
    private Quiz mQuiz;
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

        Intent intent = getActivity().getIntent();
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mCurrentSectionID = settings.getInt("position", 0);
        mQuizzes = new QuizLab(mCurrentSectionID).getQuizzes();
        mQuiz = new QuizLab(mCurrentSectionID).getQuiz();
        mSectionList = getResources().getStringArray(R.array.section_list);
        mLinearLayout = (LinearLayout) v.findViewById(R.id.progress_linear);
        setProgressBar();

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
                mFirstButton.setBackgroundResource(R.drawable.correct_answer_button_customize);
                mFirstButton.setTextColor(getResources().getColor(R.color.white));
                ButtonsEnable(false);
                if (checkAnswer(1) == false) {
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 2) {
                        answerIsTwo();
                    }
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 3) {
                        answerIsThree();
                    }
                }
            }
        });
        mSecondButton = (Button) v.findViewById(R.id.second_button);
        mSecondButton.setText(mQuizzes.get(mCurrentIndex).getmSecondChoice());
        mSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondButton.setBackgroundResource(R.drawable.correct_answer_button_customize);
                mSecondButton.setTextColor(getResources().getColor(R.color.white));
                ButtonsEnable(false);
                if (checkAnswer(2) == false) {
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 1) {
                        answerIsOne();
                    }
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 3) {
                        answerIsThree();
                    }
                }

            }
        });
        mThirdButton = (Button) v.findViewById(R.id.third_button);
        mThirdButton.setText(mQuizzes.get(mCurrentIndex).getmThirdChoice());
        mThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThirdButton.setBackgroundResource(R.drawable.correct_answer_button_customize);
                mThirdButton.setTextColor(getResources().getColor(R.color.white));
                ButtonsEnable(false);
                if (checkAnswer(3) == false) {
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 1) {
                        answerIsOne();
                    }
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 2) {
                        answerIsTwo();
                    }
                }
            }
        });
        mContinueButton = (Button) v.findViewById(R.id.continue_button);
        mContinueButton.setVisibility(INVISIBLE);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1);
                setItemVisibility(false);
                ButtonsEnable(true);
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

    //set Buttons color
    private void answerIsOne() {
        mFirstButton.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
        mFirstButton.setTextColor(getResources().getColor(R.color.red));
    }
    private void answerIsTwo() {
        mSecondButton.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
        mSecondButton.setTextColor(getResources().getColor(R.color.red));
    }
    private void answerIsThree() {
        mThirdButton.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
        mThirdButton.setTextColor(getResources().getColor(R.color.red));
    }
    
    public boolean checkAnswer(int mClickedAnswer) {
        if (mClickedAnswer == mQuizzes.get(mCurrentIndex).getmAnswerIndex()) {
            soundPool.play(good_se, 1F, 1F, 0, 0, 1F);
            setItemVisibility(true);
            mPopUpImageView.setImageResource(R.drawable.icon_correct);
            mCorrectWords = getResources().getStringArray(R.array.Correct_word_list);
            int correct = new Random().nextInt(mCorrectWords.length);
            String correctWord = (mCorrectWords[correct]);
            mPopUpTextView.setText(correctWord);
            mPopUpTextView.setTextColor(getResources().getColor(R.color.main_color));
            score++;
            return true;
        } else {
            setItemVisibility(true);
            mPopUpImageView.setImageResource(R.drawable.icon_incorrect);
            soundPool.play(bad_se, 1F, 1F, 0, 0, 1F);
            mIncorrectWords = getResources().getStringArray(R.array.Incorrect_word_list);
            int incorrect = new Random().nextInt(mIncorrectWords.length);
            String incorrectWord = (mIncorrectWords[incorrect]);
            mPopUpTextView.setText(incorrectWord);
            mPopUpTextView.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
    }


    public void setItemVisibility(boolean isVisible) {
        if(isVisible){
            mContinueButton.setVisibility(VISIBLE);
            mPopUpImageView.setVisibility(VISIBLE);
            mPopUpTextView.setVisibility(VISIBLE);
        }
        else{
            mPopUpImageView.setVisibility(INVISIBLE);
            mPopUpTextView.setVisibility(INVISIBLE);
        }
    }

    public void updateQuestion() {
        mContinueButton.setVisibility(INVISIBLE);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPool.release();
    }

    public void ButtonsEnable(boolean IsButtonEnable) {
        if (IsButtonEnable) {
            mFirstButton.setEnabled(true);
            mSecondButton.setEnabled(true);
            mThirdButton.setEnabled(true);
        } else {
            mFirstButton.setEnabled(false);
            mSecondButton.setEnabled(false);
            mThirdButton.setEnabled(false);
        }
    }

    private void setProgressBar(){
        for (int i = 0; i < mQuizzes.size(); i++) {
            ImageView mProgressBar = new ImageView(getActivity().getApplicationContext());
            mProgressBar.setId(i);
            mProgressBar.setPadding(8, 0, 8, 0);
            mProgressBar.setImageResource(R.drawable.icon_progress_gray);

            //set width and height
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
            mProgressBar.setLayoutParams(layoutParams);

            //set green color
            if(mCurrentIndex == i || i < mCurrentIndex){
                mProgressBar.setImageResource(R.drawable.icon_progress_maincolor);
            }
            mLinearLayout.addView(mProgressBar);
        }
    }
}
