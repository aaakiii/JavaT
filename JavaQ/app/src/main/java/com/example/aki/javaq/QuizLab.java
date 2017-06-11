package com.example.aki.javaq;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AKI on 2017-06-07.
 */

public class QuizLab {
    private static QuizLab sQuizLab;
    private static List<Quiz> mQuizzes;

    public static QuizLab get(Context context){
        if(sQuizLab == null){
            sQuizLab = new QuizLab();
        }
        return sQuizLab;
    }
    public QuizLab(){
        mQuizzes = new ArrayList<>();
        mQuizzes.add(new Quiz(1,"Basic Concept","Which of these lines create a variable?","myAge = 17;","String myName;","myName = \"Marty\";",2));
        mQuizzes.add(new Quiz(2,"Basic concept","What's true about 42 and \"42\"?","42 is an integer value while \"42\"is a string value","They're the same","42 can not be used for arithmetic",1));
        mQuizzes.add(new Quiz(3,"Basic concept","Can you find the mistake in this snippet?\nint myAge;\nmyAge = \"17\";\nSystem.out.print(myAge);","myAge needs to be a double-type variable","print()can't be used for integer","myAge can't take a String-type value like \"17\"",3));
        mQuizzes.add(new Quiz(4,"Basic concept","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(5,"Basic concept","Can you anticipate the result of this not-so-complex arithmetic operation?\nint number = 80;\nSystem.out.print(number/40);","40","20","20.0",2));
        mQuizzes.add(new Quiz(6,"Basic concept","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(7,"Basic concept","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(8,"Basic concept","What's the difference between int and double","int defines variable while double defines a method","They are the same","Itegers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(1,"Variables","Which of these lines create a variable?","myAge = 17;","String myName;","myName = \"Marty\";",2));
        mQuizzes.add(new Quiz(2,"Variables","What's true about 42 and \"42\"?","42 is an integer value while \"42\"is a string value","They're the same","42 can not be used for arithmetic",1));
        mQuizzes.add(new Quiz(3,"Variables","Can you find the mistake in this snippet?\nint myAge;\nmyAge = \"17\";\nSystem.out.print(myAge);","myAge needs to be a double-type variable","print()can't be used for integer","myAge can't take a String-type value like \"17\"",3));
        mQuizzes.add(new Quiz(4,"Variables","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(5,"Variables","Can you anticipate the result of this not-so-complex arithmetic operation?\nint number = 80;\nSystem.out.print(number/40);","40","20","20.0",2));
        mQuizzes.add(new Quiz(6,"Variables","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(7,"Variables","What's the difference between int and double","int defines variable while double defines a method","They are the same","Integers are whole numbers while doubles have a decimal point",3));
        mQuizzes.add(new Quiz(8,"Variables","What's the difference between int and double","int defines variable while double defines a method","They are the same","Itegers are whole numbers while doubles have a decimal point",3));
        }

    public List<Quiz> getQuizzes(){
        return mQuizzes;
    }
    public Quiz getQuiz(){
        for(Quiz quiz : mQuizzes){
            return quiz;
        }
        return null;
    }
}
