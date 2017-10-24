package com.example.aki.javaq.Domain.Usecase;

import com.example.aki.javaq.Domain.Entity.Quiz;

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
                mQuizzes.add(new Quiz(0,"Choose the data type for this value: 7","int","double","String",null,1));
                mQuizzes.add(new Quiz(0,"What is an assignment statement?","Adding a number to an int","Assigning a multiplication","Assigning a name to a variable","Assigning a value to a variable",4));
                mQuizzes.add(new Quiz(0,"Choose the appropriate data type for this value: isSwimmer","double","boolean","String","int",2));
                mQuizzes.add(new Quiz(0,"If you want your two conditions BOTH being true, what is the proper notation?","!","&&","||",null,2));
                mQuizzes.add(new Quiz(0,"Choose the correct syntax for main method.","public main(String[] args)","public void main(string[] args)","public static void main()","none of above",4));
                mQuizzes.add(new Quiz(0,"Which of the following word is not a Java keyword?","static","Integer","try",null,2));

                break;

            case 1 :
                mQuizzes.add(new Quiz(1,"What is the size of short variable?","8 bit","16 bit","32 bit",null,3));
                mQuizzes.add(new Quiz(1,"What is the default value of int variable?","0","null","not defined",null,1));
                mQuizzes.add(new Quiz(1,"What is class variable?","static variables within a class but outside of the method.","variables defined inside methods, constructors or blocks.","variables within a class but outside any method.",null,1));
                mQuizzes.add(new Quiz(1,"Why variables are useful?","They store many values in the same name","They set many names to the same value","They use same value at many place",null,3));
                break;

            case 2:
                mQuizzes.add(new Quiz(2,"What's the output of the following program?\nboolean a = true;\nboolean b = a;\nboolean c = a || b;\nSystem.out.println(c));","True","False",null,null,1));
                mQuizzes.add(new Quiz(2,"What's the output of the following program?\nboolean a = true;\nboolean b = false;\nboolean c = (a && b));","True","False",null,null,2));
                mQuizzes.add(new Quiz(2,"Are boolean only ever false?","True","False","null",null,2));
                break;

            case 3:
                mQuizzes.add(new Quiz(3,"When if condition will be executed?","Anytime","When the condition is true","When the condition is false",null,2));
                mQuizzes.add(new Quiz(3,"What's the output of the following program?\nString a = \"mom\";\nString b = \"dad\";\nif(a != b){\n\tSystem.out.println(a));\n}","mom","dad","Both of above",null,1));
                mQuizzes.add(new Quiz(3,"Is it allowed to put if statement inside another if statement?","Yes","No",null,null,1));
                mQuizzes.add(new Quiz(3,"What does this operator == mean?","Assigning a value","Checking if values are equal","Checking if values are not equal",null,2));
                mQuizzes.add(new Quiz(3,"What's the output of the following program?\nint temp = 20;\nif(temp > 35){\n\tSystem.out.println(\"It's too Hot\"));\n}else if(temp > 10){\n\tSystem.out.println(\"Not too cold.\"));\n}","It's too Hot!!","Not too cold.","Error",null,2));
                mQuizzes.add(new Quiz(3,"Is the following program collect?\nint temp = 10;\nelse if(temp > 30){\n\tSystem.out.println(\"It's really hot...\")); \n}else if(temp > 20){\n\tSystem.out.println(\"It's nice temperature.\"));\n}","Yes","No",null,null,2));
                break;

            case 4:
                mQuizzes.add(new Quiz(4,"What is the output of the following code?\nint[] arry = new int[4];\nystem.out.println(arry[4]));","4","error because array is not initialized","error,because index is out of range",null,3));
                mQuizzes.add(new Quiz(4,"What number will be printed by the following code?\nint[] nums = {1,2,3,4,5,6};\nfor(int i = 0; i < 5; i++){\n\tSystem.out.println(nums[i + 1]));\n}","0-6","1-6","2-6",null,3));
                mQuizzes.add(new Quiz(4,"Which of the following code is an invalid declaration?","String[] arry = new String[3];","String arry[] = new String[4];","String[] arry = new String[0];","all are valid",4));
                mQuizzes.add(new Quiz(4,"What is the hegihest index value of the following code?\nint[] values = new int[x];","x","x+1","x-1",null,3));
                mQuizzes.add(new Quiz(4,"The length of the following array is int[] grades = new int[7];","6","8","7",null,3));
                mQuizzes.add(new Quiz(4,"Which of the following code gets the number of integers in the following array?\nint[] nums = new int[30];","int size = nums.length;","int size = nums.length());","int size = nums.size());",null,1));
                mQuizzes.add(new Quiz(4,"What is the output of the following code?\nint[] arry = new int[5];\nSystem.out.println(arr[0]));","error,bacause array is not initialized","error, because index must be greater than 0","0",null,3));
                mQuizzes.add(new Quiz(4,"What is the value of arry[4]?\nint[] arry = {1,2,3,4,5};","3","4","5",null,3));
                break;

            case 5:
                mQuizzes.add(new Quiz(5, "Section number 5", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point","", 3));
                break;

            case 6:
                mQuizzes.add(new Quiz(6, "Section number 6", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point","", 3));
                break;

            case 7:
                mQuizzes.add(new Quiz(7, "Section number 7", "int defines variable while double defines a method", "They are the same", "Integers are whole numbers while doubles have a decimal point","", 3));
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
