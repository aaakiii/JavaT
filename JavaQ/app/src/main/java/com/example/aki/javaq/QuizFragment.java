package com.example.aki.javaq;

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


import java.util.List;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class QuizFragment extends Fragment {
    private ImageView mProgressBar1;
    private ImageView mProgressBar;
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
    private int mCurrentSectionID;
    private String[] mSectionList;
    private List<Quiz> mQuizzes;
    private String[] mCorrectWords;
    private String[] mIncorrectWords;
    private static final String EXTRA_SCORE = "com.example.aki.javaq.score";
    public static final String KEYWORD_PREF_SCORE = "JavaQ_keyword_score";
    private LinearLayout mLinearLayout;
    private ImageView mPopUpImageView;
    private TextView mPopUpTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        mCurrentSectionID = intent.getIntExtra(QuizSectionFragment.EXTRA_SECTION_POSITON, 0);
        mQuizzes = new QuizLab(mCurrentSectionID).getQuizzes();
        mQuiz = new QuizLab(mCurrentSectionID).getQuiz();
        View v = inflater.inflate(R.layout.quiz_fragment, container, false);
        mSectionList = getResources().getStringArray(R.array.section_list);
        mLinearLayout = (LinearLayout)v.findViewById(R.id.progress_linear);
        mProgressBar1 = new ImageView(getContext());
        mProgressBar1.setImageResource(R.drawable.icon_progress_maincolor);
        mLinearLayout.addView(mProgressBar1);
        for(int i = 1; i < mQuizzes.size(); i++){
            mProgressBar = new ImageView(getContext());
            mProgressBar.setImageResource(R.drawable.icon_progress_gray);
            mLinearLayout.addView(mProgressBar);
        }
        mContinueButton = (Button) v.findViewById(R.id.continue_button);
        mContinueButton.setVisibility(INVISIBLE);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1);
                mPopUpImageView.setVisibility(INVISIBLE);
                mPopUpTextView.setVisibility(INVISIBLE);
                ButtonsEnable(true);

                for(int j = 1; j < mQuizzes.size(); j++){

//                   mProgressBar.setImageResource(R.drawable.icon_progress_maincolor);
                }
                if (mCurrentIndex == mQuizzes.size()) {
                    Intent intent = new Intent(getActivity().getApplication(), QuizResultActivity.class );
                    intent.putExtra(EXTRA_SCORE, score);
                    startActivity(intent);

                    //SharedPreferences
                    SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
//                    editor.putString(mSectionList[mCurrentSectionID] + KEYWORD_PREF_SCORE, String.valueOf(mCurrentSectionID) + "-" + String.valueOf(score) );
                    editor.putInt(mSectionList[mCurrentSectionID] + KEYWORD_PREF_SCORE, score );
                    editor.apply();
                } else {
                    mCurrentIndex %= mQuizzes.size();
                    updateQuestion();
                }
            }
        });
        mQuizText = (TextView) v.findViewById(R.id.question_item);
        mQuizText.setText(mQuizzes.get(mCurrentIndex).getmQuestionText());
        mPopUpImageView = (ImageView) v.findViewById(R.id.answer_image_popup);
        mPopUpImageView.setVisibility(INVISIBLE);
        mPopUpTextView = (TextView)v.findViewById(R.id.answer_text_popup);
        mPopUpTextView.setVisibility(INVISIBLE);
        mFirstButtons = (Button) v.findViewById(R.id.first_button);
        mFirstButtons.setText(mQuizzes.get(mCurrentIndex).getmFirstChoice());
        mFirstButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstButtons.setBackgroundResource(R.drawable.correct_answer_button_customize);
                mFirstButtons.setTextColor(getResources().getColor(R.color.white));
                ButtonsEnable(false);
                if (checkAnswer(1) == false) {
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 2) {
                        mSecondButtons.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
                        mSecondButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 3) {
                        mThirdButtons.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
                        mThirdButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });
        mSecondButtons = (Button) v.findViewById(R.id.second_button);
        mSecondButtons.setText(mQuizzes.get(mCurrentIndex).getmSecondChoice());
        mSecondButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondButtons.setBackgroundResource(R.drawable.correct_answer_button_customize);
                mSecondButtons.setTextColor(getResources().getColor(R.color.white));
                ButtonsEnable(false);
                if (checkAnswer(2) == false) {
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 1) {
                        mFirstButtons.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
                        mFirstButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 3) {
                        mThirdButtons.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
                        mThirdButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }

            }
        });
        mThirdButtons = (Button) v.findViewById(R.id.third_button);
        mThirdButtons.setText(mQuizzes.get(mCurrentIndex).getmThirdChoice());
        mThirdButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThirdButtons.setBackgroundResource(R.drawable.correct_answer_button_customize);
                mThirdButtons.setTextColor(getResources().getColor(R.color.white));
                ButtonsEnable(false);
                if (checkAnswer(3) == false) {
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 1) {
                        mFirstButtons.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
                        mFirstButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if (mQuizzes.get(mCurrentIndex).getmAnswerIndex() == 2) {
                        mSecondButtons.setBackgroundResource(R.drawable.incorrect_answer_button_customize);
                        mSecondButtons.setTextColor(getResources().getColor(R.color.red));
                    }
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

    public boolean checkAnswer(int mClickedAnswer) {
        if (mClickedAnswer == mQuizzes.get(mCurrentIndex).getmAnswerIndex()) {
            soundPool.play(good_se, 1F, 1F, 0, 0, 1F);
            mContinueButton.setVisibility(VISIBLE);
            mPopUpImageView.setVisibility(VISIBLE);
            mPopUpImageView.setImageResource(R.drawable.icon_correct);
            mCorrectWords = getResources().getStringArray(R.array.Correct_word_list);
            int correct = new Random().nextInt(mCorrectWords.length);
            String correctWord = (mCorrectWords[correct]);
            mPopUpTextView.setVisibility(VISIBLE);
            mPopUpTextView.setText(correctWord);
            mPopUpTextView.setTextColor(getResources().getColor(R.color.main_color));
            score++;
            return true;
        } else {
            mContinueButton.setVisibility(VISIBLE);
            mPopUpImageView.setVisibility(VISIBLE);
            mPopUpImageView.setImageResource(R.drawable.icon_incorrect);
            soundPool.play(bad_se, 1F, 1F, 0, 0, 1F);
            mIncorrectWords = getResources().getStringArray(R.array.Incorrect_word_list);
            int incorrect = new Random().nextInt(mIncorrectWords.length);
            String incorrectWord = (mIncorrectWords[incorrect]);
            mPopUpTextView.setVisibility(VISIBLE);
            mPopUpTextView.setText(incorrectWord);
            mPopUpTextView.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
    }

    public void updateQuestion() {
        mContinueButton.setVisibility(INVISIBLE);
        String question = mQuizzes.get(mCurrentIndex).getmQuestionText();
        mQuizText.setText(question);
        mFirstButtons.setBackgroundResource(R.drawable.answer_button_customize);
        mFirstButtons.setTextColor(getResources().getColor(R.color.main_text));
        String first_button = mQuizzes.get(mCurrentIndex).getmFirstChoice();
        mFirstButtons.setText(first_button);
        mSecondButtons.setBackgroundResource(R.drawable.answer_button_customize);
        mSecondButtons.setTextColor(getResources().getColor(R.color.main_text));
        String second_button = mQuizzes.get(mCurrentIndex).getmSecondChoice();
        mSecondButtons.setText(second_button);
        mThirdButtons.setBackgroundResource(R.drawable.answer_button_customize);
        mThirdButtons.setTextColor(getResources().getColor(R.color.main_text));
        String third_button = mQuizzes.get(mCurrentIndex).getmThirdChoice();
        mThirdButtons.setText(third_button);
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPool.release();
    }
    public void ButtonsEnable(boolean IsButtonEnable){
        if(IsButtonEnable) {
            mFirstButtons.setEnabled(true);
            mSecondButtons.setEnabled(true);
            mThirdButtons.setEnabled(true);
        }
        else{
            mFirstButtons.setEnabled(false);
            mSecondButtons.setEnabled(false);
            mThirdButtons.setEnabled(false);
        }
    }
}
