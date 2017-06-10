package com.example.aki.javaq;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class QuizFragment extends Fragment {
    private TextView mQuizText;
    private Button mFirstButtons;
    private Button mSecondButtons;
    private Button mThirdButtons;
    private int mCurrentIndex;
    private Button mContinueButton;
    private int score = 0;
    private static final String KEY_INDEX = "index";


    private SoundPool soundPool;
    private int good_se;
    private int bad_se;

    private Quiz[] mQuiz = new Quiz[]{
            new Quiz(0,"Basic Concept","Which of these lines create a variable?","myAge = 17;","String myName;","myName = \"Marty\";",2),
            new Quiz(1,"Basic concept","What's true about 42 and \"42\"?","42 is an integer value while \"42\"is a string value","They're the same","42 can not be used for arithmetic",1),
            new Quiz(2,"Basic concept","Can you find the mistake in this snippet?\nint myAge;\nmyAge = \"17\";\nSystem.out.print(myAge);","myAge needs to be a double-type variable","print()can't be used for integer","myAge can't take a String-type value like \"17\"",3),
            new Quiz(3,"Basic concept","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3),
            new Quiz(4,"Basic concept","Can you anticipate the result of this not-so-complex arithmetic operation?\nint number = 80;\nSystem.out.print(number/40);","40","20","20.0",2)
    };
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        mQuizText = (TextView) v.findViewById(R.id.question_item);
        mQuizText.setText(mQuiz[mCurrentIndex].getmQuestionText());
        mFirstButtons = (Button) v.findViewById(R.id.first_button);
        mFirstButtons.setText(mQuiz[mCurrentIndex].getmFirstChoice());
        mFirstButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mFirstButtons.setBackgroundResource(R.color.sub_color);
                mFirstButtons.setTextColor(getResources().getColor(R.color.white));
                if(checkAnswer(1) == false){
                    if(mQuiz[mCurrentIndex].getmAnswerIndex() == 2){
                        mSecondButtons.setBackgroundResource(R.color.white);
                        mSecondButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if(mQuiz[mCurrentIndex].getmAnswerIndex() == 3){
                        mThirdButtons.setBackgroundResource(R.color.white);
                        mThirdButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });
        mSecondButtons= (Button) v.findViewById(R.id.second_button);
        mSecondButtons.setText(mQuiz[mCurrentIndex].getmSecondChoice());
        mSecondButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mSecondButtons.setBackgroundResource(R.color.sub_color);
                mSecondButtons.setTextColor(getResources().getColor(R.color.white));
                if(checkAnswer(2) == false){
                    if(mQuiz[mCurrentIndex].getmAnswerIndex() == 1){
                        mFirstButtons.setBackgroundResource(R.color.white);
                        mFirstButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if(mQuiz[mCurrentIndex].getmAnswerIndex() == 3){
                        mThirdButtons.setBackgroundResource(R.color.white);
                        mThirdButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                }

            }
        });
        mThirdButtons = (Button) v.findViewById(R.id.third_button);
        mThirdButtons.setText(mQuiz[mCurrentIndex].getmThirdChoice());
        mThirdButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mThirdButtons.setBackgroundResource(R.color.sub_color);
                mThirdButtons.setTextColor(getResources().getColor(R.color.white));
                if(checkAnswer(3) == false){
                    if(mQuiz[mCurrentIndex].getmAnswerIndex() == 1){
                        mFirstButtons.setBackgroundResource(R.color.white);
                        mFirstButtons.setTextColor(getResources().getColor(R.color.red));
                    }
                    if(mQuiz[mCurrentIndex].getmAnswerIndex() == 2){
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
                mCurrentIndex = (mCurrentIndex+1)%mQuiz.length;
                updateQuestion();
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(2)
                    .build();
        }
        good_se = soundPool.load(getContext(), R.raw.good, 1);
        bad_se = soundPool.load(getContext(), R.raw.bad, 1);

        return v;
    }
    public void updateQuestion(){
        mContinueButton.setVisibility(INVISIBLE);
        String question = mQuiz[mCurrentIndex].getmQuestionText();
        mQuizText.setText(question);
        mFirstButtons.setBackgroundResource(R.color.white);
        mFirstButtons.setTextColor(getResources().getColor(R.color.main_text));
        String first_button = mQuiz[mCurrentIndex].getmFirstChoice();
        mFirstButtons.setText(first_button);
        mSecondButtons.setBackgroundResource(R.color.white);
        mSecondButtons.setTextColor(getResources().getColor(R.color.main_text));
        String second_button = mQuiz[mCurrentIndex].getmSecondChoice();
        mSecondButtons.setText(second_button);
        mThirdButtons.setBackgroundResource(R.color.white);
        mThirdButtons.setTextColor(getResources().getColor(R.color.main_text));
        String third_button = mQuiz[mCurrentIndex].getmThirdChoice();
        mThirdButtons.setText(third_button);
    }

    public boolean checkAnswer(int mClickedAnswer){
        if(mClickedAnswer == mQuiz[mCurrentIndex].getmAnswerIndex()){
            mCurrentIndex = (mCurrentIndex+1)%mQuiz.length;
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

    @Override
    public void onPause() {
        super.onPause();
        soundPool.release();
    }
//    public void retryQuestions(View view){
//        Intent inetnt = new Intent(getActivity(), QuizActivity.class);
//        startActivity(inetnt);
//    }


}
