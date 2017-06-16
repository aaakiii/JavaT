package com.example.aki.javaq;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AKI on 2017-06-07.
 */

public class QuizLab {

    private List<Quiz> mQuizzes;


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
                break;

            case 1 :
                mQuizzes.add(new Quiz(1, "What are valid ways of declaring and initializing a variable?", "int a = 6;", "double b = 1;", "int c = null", 1));
                mQuizzes.add(new Quiz(1, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                mQuizzes.add(new Quiz(1, "What's the difference between int and double", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                break;

            case 2:
                mQuizzes.add(new Quiz(2, "How can we check if a variable called myNumber equals 15?", "myNumber == 15", "myNumber && 15", "myNumber = 15", 1));
                break;

            case 3:
                mQuizzes.add(new Quiz(3, "Section number 3", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                break;

            case 4:
                mQuizzes.add(new Quiz(4, "Section number 4", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                break;

            case 5:
                mQuizzes.add(new Quiz(5, "Section number 5", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                break;

            case 6:
                mQuizzes.add(new Quiz(6, "Section number 6", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                break;

            case 7:
                mQuizzes.add(new Quiz(7, "Section number 7", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point", 3));
                break;
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
