package com.example.aki.javaq;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AKI on 2017-06-07.
 */

public class QuizLab {
    private static QuizLab sQuizLab;
    private List<Quiz> mQuizzes;

//    public static QuizLab get(Context context, int sectionID) {
//        if (sQuizLab == null) {
//            sQuizLab = new QuizLab(sectionID);
//        }
//        return sQuizLab;
//    }

    public QuizLab(int sectionID) {
        mQuizzes = new ArrayList<>();
        createArray(sectionID);
    }

    public void createArray(int sectionID){
        switch (sectionID){
            case 0 :
                mQuizzes.add(new Quiz(0, "Which of these lines create a variable?", "myAge = 17;", "String myName;", "myName = \"Marty\";", 2));
                mQuizzes.add(new Quiz(0, "What's true about 42 and \"42\"?", "42 is an integer value while \"42\"is a string value", "They're the same", "42 can not be used for arithmetic", 1));
                mQuizzes.add(new Quiz(0, "Can you find the mistake in this snippet?\nint myAge;\nmyAge = \"17\";\nSystem.out.print(myAge);", "myAge needs to be a double-type variable", "print()can't be used for integer", "myAge can't take a String-type value like \"17\"", 3));
                mQuizzes.add(new Quiz(0, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                mQuizzes.add(new Quiz(0, "Can you anticipate the result of this not-so-complex arithmetic operation?\nint number = 80;\nSystem.out.print(number/40);", "40", "20", "20.0", 2));
                mQuizzes.add(new Quiz(0, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                mQuizzes.add(new Quiz(0, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                mQuizzes.add(new Quiz(0, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 1 :
                mQuizzes.add(new Quiz(1, "Section number 1", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                mQuizzes.add(new Quiz(1, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                mQuizzes.add(new Quiz(1, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 2:
                mQuizzes.add(new Quiz(2, "Section number 2", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 3:
                mQuizzes.add(new Quiz(3, "Section number 2", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 4:
                mQuizzes.add(new Quiz(4, "Section number 2", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 5:
                mQuizzes.add(new Quiz(5, "Section number 2", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 6:
                mQuizzes.add(new Quiz(6, "Section number 2", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));

            case 7:
                mQuizzes.add(new Quiz(7, "Section number 2", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
        }
    }

    public List<Quiz> getQuizzes() {
        return mQuizzes;
    }

    public Quiz getQuiz() {
        for (Quiz quiz : mQuizzes) {
            return quiz;
        }
        return null;
    }
}
