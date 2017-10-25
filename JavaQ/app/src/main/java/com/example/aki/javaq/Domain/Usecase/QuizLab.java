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

            // String
            case 5:
                mQuizzes.add(new Quiz(5, "What is the output?\n" +
                        "\n" +
                        "String paris = \"Jack went to Paris,\"\n" +
                        "String find = \"Jack went to Tokyo.\"\n" +
                        "\n" +
                        "if (paris.startsWith(\"Jack\") && find.startsWith(\"Jack\"))\n" +
                        "System.out.println(paris + find);\n" +
                        "else\n" +
                        "System.out.println(\"Different Starts\");",
                        "Jack went to Paris,\n" + "Jack went to Tokyo..",
                        "Jack went to Paris, Jack went to Tokyo..",
                        "Different Starts",
                        null,
                        2));

                mQuizzes.add(new Quiz(5, "What is the output?\n" +
                        "\n" +
                        "public static void main(String arry[]) {\n" +
                        "\tString a = \"pool\";\n" +
                        "\tString b = new String(\"pool\");\n" +
                        "\tSystem.out.print(a.equals(b));\n" +
                        "\tSystem.out.print(a==b)\n" +
                        "}",
                        "truetrue",
                        "falsefalse",
                        "truefalse",
                        "falsetrue",
                        3));

                mQuizzes.add(new Quiz(5, "What is the data type of \"something\"?\n" +
                        "\n" +
                        "String something:",
                        "null",
                        "String",
                        "reference to String",
                        null,
                        2));


                mQuizzes.add(new Quiz(5, "What is the output?\n" +
                        "\n" +
                        "public static void main(String arry[]) {\n" +
                        "\tString a = \"pool\";\n" +
                        "\tString b = new String(\"pool\");\n" +
                        "\tSystem.out.print(a.equals(b));\n" +
                        "\tSystem.out.print(a==b)\n" +
                        "}",
                        "null",
                        "String",
                        "reference to String",
                        null,
                        2));

                mQuizzes.add(new Quiz(5, "What sort of thing is \"Dance lesson\" as is the following:\n" +
                        "\n" +
                        "String myString = \"Dance lesson\";",
                        "Quoted String",
                        "String shortcut",
                        "String literal",
                        null,
                        3));

                mQuizzes.add(new Quiz(5, "What is the output?\n" +
                        "\n" +
                        "String strA = \" Small \";\n" +
                        "String strB = \" English \";\n" +
                        "String strC = \" Garden \";\n" +
                        "String result = strA.trim() + strB + strC.trim();",
                        "\"SmallEnglishGarden\"",
                        "\"Small English Garden\"",
                        "\" Small English Garden \"",
                        null,
                        2));

                mQuizzes.add(new Quiz(5, "What value is assigned to a reference value to show that there is no object?",
                        "null",
                        "\"\"",
                        "0",
                        "void",
                        1));
                break;

            // Array List
            case 6:
                mQuizzes.add(new Quiz(6, "What is the declaration of an ArrayList with an initial capacity of 10 references to Object.\n",
                        "ArrayList<Object> list = new ArrayList<Object>(10);",
                        "ArrayList list[10] = new ArrayList() ;",
                        "ArrayList[Object] list = new ArrayList(10);",
                        null,
                        1));

                mQuizzes.add(new Quiz(6, "What element will be at index 2 of the list?\n" +
                        "\n" +
                        "ArrayList<String> list = new ArrayList<String>() ;\n" +
                        "\n" +
                        "list.add( \"A\" );\n" +
                        "list.add( \"B\" );\n" +
                        "list.add( \"C\" );\n" +
                        "list.add( 0, \"E\" );",
                        "A",
                        "B",
                        "C",
                        "D",
                        3));

                mQuizzes.add(new Quiz(6, "Which of the code will replace the element \"C\" with \"Z\" ?\n" +
                        "\n" +
                        "ArrayList<String> list = new ArrayList<String>() ;\n" +
                        "\n" +
                        "list.add( \"A\" );\n" +
                        "list.add( \"B\" );\n" +
                        "list.add( \"C\" );",
                        "list.set( \"Z\", \"C\" );",
                        "list[2] = \"Z\" ;",
                        "list.set( list.indexOf(\"C\"), \"Z\" );",
                        null,
                        3));

                mQuizzes.add(new Quiz(6, "Which of the declarations would be appropriate for a list that is expected to contain integers?",
                        "ArrayList<int> list = new ArrayList<int>() ;",
                        "ArrayList<Integer> list = new ArrayList<Integer>() ;",
                        "ArrayList list = new ArrayList() ;",
                        null,
                        2));

                mQuizzes.add(new Quiz(6, "Which of these method of ArrayList class is used to obtain present size of an object?",
                        "size()",
                        "length()",
                        "capacity()",
                        "index()",
                        1));

                mQuizzes.add(new Quiz(6, "What is the output?\n" +
                        "\n" +
                        "ArrayList list = new ArrayList();\n" +
                        "list.add(\"A\");\n" +
                        "list.add(0, \"B\");\n" +
                        "System.out.println(obj.size());",
                        "0",
                        "1",
                        "2",
                        null,
                        3));
                break;

            // loop
            case 7:
                mQuizzes.add(new Quiz(7, "What is the output?\n" +
                        "\n" +
                        "for (int i = 0; i < 5; i++) {\n" +
                        "\tSystem.out.print( i );\n" +
                        "}",
                        "12345",
                        "01234",
                        "012345",
                        null,
                        2));

                mQuizzes.add(new Quiz(7, "What is the output?\n" +
                        "\n" +
                        "for (int i = 10; i > 5; i--) {\n" +
                        "\tSystem.out.print( i  + \" \" );\n" +
                        "}",
                        "10 9 8 7 6",
                        "10 11 12 13 14 15",
                        "10 9 8 7 6 5",
                        null,
                        1));

                mQuizzes.add(new Quiz(7, "What is the output?\n" +
                        "\n" +
                        "var i = 0;\n" +
                        "while (i < 3) {\n" +
                        "    print(\"A\");\n" +
                        "    i++;\n" +
                        "}",
                        "AAA",
                        "AA",
                        "AAAA",
                        null,
                        1));

                mQuizzes.add(new Quiz(7, "What is the output?\n" +
                        "\n" +
                        "for (int i = 1; i < 15; i = i+3) {     \n" +
                        "   out.print(i + \"\");\n" +
                        "}",
                        "1 4 7 10 13",
                        "4 7 10 13 16",
                        "15",
                        null,
                        1));

                mQuizzes.add(new Quiz(7, "What is the output?\n" +
                        "\n" +
                        "for (int i = 10; i > 0; i = i - 4) {\n" +
                        "   out.println(i);\n" +
                        "}",
                        "-4",
                        "0",
                        "-2",
                        "4",
                        3));

                mQuizzes.add(new Quiz(7, "Which looping process is best used when the number of iterations is known?",
                        "while",
                        "for",
                        "do while",
                        null,
                        2));

                mQuizzes.add(new Quiz(7, "A continue statement causes execution to skip to...",
                        "the statement following the continue statement",
                        "the end of the program",
                        "the first statement after the loop",
                        "the next iteration of the loop",
                        4));

                mQuizzes.add(new Quiz(7, "What is the output?\n" +
                        "\n" +
                        "var i = 0;\n" +
                        "while (i < 0) {\n" +
                        "\tprintln(\"hi\");\n" +
                        "}",
                        "It will output an error",
                        "It will output \"hi\"",
                        "It won't output anything",
                        null,
                        3));
                break;

            // methods
            case 8:
                mQuizzes.add(new Quiz(8, "The following code in the main() is valid?\n" +
                        "\n" +
                        "public static void main(String [] args) {\n" +
                        "\tint value;\n" +
                        "\tvalue = getDeposit(amount);\n" +
                        "}\n" +
                        "\n" +
                        "public static int getDeposit() {\n" +
                        "\t return 4;\n" +
                        "}",
                        "Yes",
                        "No",
                        null,
                        null,
                        2));

                mQuizzes.add(new Quiz(8, "To call the method \"someMethod\" with the number 100 and 5 retries what would you write in the main() method? \n" +
                        "\n" +
                        "public static void someMethod(int number, int retries) {\n" +
                        "\t// code is hidden\n" +
                        "}",
                        "someMethod(100,5);",
                        "someMethod(100:5);",
                        "someMethod(100 5);",
                        "someMethod(100+5);",
                        1));

                mQuizzes.add(new Quiz(8, "In the following method declaration, how many formal parameters are there?\n" +
                        "\n" +
                        "public static double addition(double value, double value2) {\n" +
                        "\treturn value + value2;\n" +
                        "}",
                        "0",
                        "1",
                        "2",
                        "3",
                        3));

                mQuizzes.add(new Quiz(8, "The call to the method installAppl() is valid?\n" +
                        "\n" +
                        "public static void main(String [] args) {\n" +
                        "\tinstallApp(\"Facebook\", \"version 1.5\");\n" +
                        "}\n" +
                        "\n" +
                        "public static void installApplication(String name, int version) {\n" +
                        "\t// code is hidden\n" +
                        "}",
                        "Yes",
                        "No",
                        null,
                        null,
                        2));

                mQuizzes.add(new Quiz(8, "In the following method declaration, what is the name of the method?\n" +
                        "\n" +
                        "public static void setColor(String color) {\n" +
                        "\t// code is hidden\n" +
                        "}",
                        "public",
                        "static",
                        "setColor",
                        "color",
                        3));

                mQuizzes.add(new Quiz(8, "The call to the method changeNum() is valid?\n" +
                        "\n" +
                        "public static void main(String [] args) {\n" +
                        "int x = 20;\n" +
                        "changeNum(x + 5);\n" +
                        "}\n" +
                        "public static void changeNum(int number) {\n" +
                        "\t// code is hidden\n" +
                        "}",
                        "Yes",
                        "No",
                        null,
                        null,
                        1));

                mQuizzes.add(new Quiz(8, "What is the value that is returned in the following method?\n" +
                        "\n" +
                        "public static int getNum() {\n" +
                        "\tint i;\n" +
                        "\ti = 20;\n" +
                        "\tif (i < 50) {\n" +
                        "\t\treturn 50;\n" +
                        "\t}\n" +
                        "\treturn i;\n" +
                        "}",
                        "20 and 50",
                        "20",
                        "50",
                        null,
                        3));
                break;

            // classes
            case 9:
                mQuizzes.add(new Quiz(9, "Which of the field declaration is legal within the body of an interface?",
                        "int index = 5;",
                        "protected static int index = 5;",
                        "private final static int index = 5;",
                        null,
                        1));

                mQuizzes.add(new Quiz(9, "An abstract can class define both abstract methods and non-abstract methods?",
                        "Yes",
                        "No",
                        null,
                        null,
                        1));

                mQuizzes.add(new Quiz(9, "A constructor is used to...",
                        "Inherit a class",
                        "Initialize a newly created object.",
                        "Import packages",
                        null,
                        2));

                mQuizzes.add(new Quiz(9, "Which of the following are invalid Constructors?",
                        "public Person(){}",
                        "Person(){}",
                        "private void Person(){}",
                        null,
                        3));

                mQuizzes.add(new Quiz(9, "In a constructor, where can you place a call to the super class constructor?",
                        "Anywhere",
                        "The first statement in the constructor",
                        "The last statement in the constructor",
                        null,
                        2));

                mQuizzes.add(new Quiz(9, "Here is an abstract method defined in the parent:\n" +
                        "\n" +
                        "public abstract int getSum ( int[] arr );\n" +
                        "\n" +
                        "Which of the following is required in a non-abstract child?",
                        "public int getSum ( int[] arr ) { . . . }",
                        "public int getSum ( long[] arr ) { . . . }",
                        "public abstract int getSum ( int[] arr ) { . . . }",
                        null,
                        1));

                mQuizzes.add(new Quiz(9, "What is the output?\n" +
                        "\n" +
                        "class Test {\n" +
                        "\tint i;\n" +
                        "} \n" +
                        "class Main {\n" +
                        "\tpublic static void main(String args[]) { \n" +
                        "\t\tTest t; \n" +
                        "\t\tSystem.out.println(t.i); \n" +
                        "\t}\n" +
                        "}",
                        "runtime error",
                        "compile error",
                        "0",
                        null,
                        2));

                mQuizzes.add(new Quiz(9, "Which describes the following class declaration?\n" +
                        "\n" +
                        "class Circle extends Shape {\n" +
                        "\tPoint center;\n" +
                        "\tdouble radius;\n" +
                        "}",
                        "A circle has a shape that is a center point and a radius.",
                        "A shape is a circle that has a center point and a radius.",
                        "A circle is a shape that has a center point and a radius.",
                        null,
                        3));
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
