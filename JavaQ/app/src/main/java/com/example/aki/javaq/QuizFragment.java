package com.example.aki.javaq;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class QuizFragment extends Fragment {
    private TextView quiz_text;
    private Button[] buttons;
    private Quiz mQuiz;
    private Button continue_button;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Quiz.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);

        quiz_text = (TextView) v.findViewById(R.id.question_item);
        buttons = new Button[3];
        buttons[0] = (Button) v.findViewById(R.id.first_button);
        buttons[1] = (Button) v.findViewById(R.id.second_button);
        buttons[2] = (Button) v.findViewById(R.id.third_button);
        continue_button = (Button) v.findViewById(R.id.continue_button);

        Intent intent = getActivity().getIntent();
        if(intent != null){
            mQuiz = (Quiz) intent.getSerializableExtra("Quiz");
            showQuestion();
        }
        return v;
    }
    void showQuestion(){
        if(mQuiz != null){
            quiz_text.setText(mQuiz.getmQuestionText());
            for(int i = 0; i< buttons.length; i++){
                buttons[i].setText(mQuiz.getmChoices()[i]);
            }
            continue_button.setVisibility(INVISIBLE);
            for(Button b : buttons){
                b.setEnabled(true);
            }
        }
    }
    public void answerQuestion(View view){
        for(Button b : buttons){
            b.setEnabled(false);
        }
        for(int i = 0; i< buttons.length; i++){
            if(view.getId() == buttons[i].getId()){
                if(i == mQuiz.getmAnswerIndex()){
                    Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
                    continue_button.setVisibility(VISIBLE);
                }
                else{
                    Toast.makeText(getActivity(), "Incorrect", Toast.LENGTH_SHORT).show();
                    continue_button.setVisibility(VISIBLE);
                }
            }
        }
    }
    public void nextQuestion(View view){
        //Quizのアップデートメソッドを読んでくる
        mQuiz = Quiz.getQuiz(mQuiz.getmQuizNumber()+1);
        if(mQuiz != null){
            showQuestion();
        }
        else{
            Intent intent = new Intent(getActivity(),ResultActivity.class);
            startActivity(intent);
        }
    }
    public void retryQuestions(View view){
        Intent inetnt = new Intent(getActivity(), QuizActivity.class);
    }

}
