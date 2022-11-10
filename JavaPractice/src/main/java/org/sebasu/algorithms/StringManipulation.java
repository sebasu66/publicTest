package org.sebasu.algorithms;

import org.apache.logging.log4j.util.TriConsumer;

public class StringManipulation {

    //Check that the string is a palindrome

    static final String ALPHANUMERIC_REGEX = "[^a-zA-Z0-9]";


    public static boolean isPalindrome(String str) {
        //Remove all non alphanumeric characters
        str = str.replaceAll(ALPHANUMERIC_REGEX, "");
        //Convert to lower case
        str = str.toLowerCase();
        //Reverse the string
        String reverse = new StringBuilder(str).reverse().toString();
        //Compare the original string with the reversed string
        return str.equals(reverse);
    }

    //Check if a string is a permutation of another string
    public static boolean isPermutation(String str1, String str2) {
        //Remove all non alphanumeric characters
        str1 = str1.replaceAll(ALPHANUMERIC_REGEX, "");
        str2 = str2.replaceAll(ALPHANUMERIC_REGEX, "");
        //Convert to lower case
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        //Sort the strings
        str1 = sort(str1);
        str2 = sort(str2);
        //Compare the strings
        return str1.equals(str2);
    }

    //Sort a string
    private static String sort(String str) {
        char[] charArray = str.toCharArray();
        java.util.Arrays.sort(charArray);
        return new String(charArray);
    }

    //Replace all spaces in a string with %20
    public static String replaceSpaces(String str) {
        //Convert to char array
        char[] charArray = str.toCharArray();
        //Count the number of spaces
        int spaceCount = 0;
        for (char c : charArray) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        //Create a new char array with the correct length
        char[] newCharArray = new char[charArray.length + spaceCount * 2];
        //Copy the characters from the old array to the new array
        int j = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ') {
                newCharArray[j] = '%';
                newCharArray[j + 1] = '2';
                newCharArray[j + 2] = '0';
                j += 3;
            } else {
                newCharArray[j] = charArray[i];
                j++;
            }
        }
        //Convert the new char array to a string
        return new String(newCharArray);
    }

    //CHECK THAT THERE AREN'T OCCURRENCES OF B BEFORE AN A
    //constraint, the input string can only contain a's and b's
    public static boolean noBsBeforeAs(String str) {
        //check special cases
        if (str == null || str.length() == 0) {
            return true;
        }
        if (str.length() == 1) {
            return true;
            //both "a" and "b" are valid
        }
        boolean mainResult = true;

        //check that there aren't occurrences of b before an a
        TriConsumer<String, Boolean, Long> callback = (threadName, result, iterations) -> {
            System.out.println(threadName + " has completed, result is: " + result + ", iterations: " + iterations);
        };

        InvalidPatternCheckThread invalidPatternCheckThread = new InvalidPatternCheckThread(callback, str);
         OppositeEndsCheck oppositeEndsCheck = new OppositeEndsCheck(callback, str);

        oppositeEndsCheck.start();
        invalidPatternCheckThread.start();

        boolean waiting = true;
        while (waiting) {
            if (!oppositeEndsCheck.isAlive()) {
                waiting = false;
                mainResult = oppositeEndsCheck.getResult();
            }
            if (!invalidPatternCheckThread.isAlive()) {
                waiting = false;
                mainResult = invalidPatternCheckThread.getResult();
            }
            if (waiting) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mainResult;

    }

    static class ThreadProcess extends Thread {

        Boolean result;

        public Boolean getResult() {
            return result;
        }
        Long steps;
        String str;
        TriConsumer<String, Boolean, Long> callback;

        public ThreadProcess(TriConsumer callback, String input) {
          //  this.callback = callback;
            this.str = input;
            steps = 0L;
            result = true;
        }
    }


    static class InvalidPatternCheckThread extends ThreadProcess {

        public InvalidPatternCheckThread(TriConsumer callback, String input) {
            super(callback, input);
        }

        @Override
        public void run() {


            //convert to char array
            char[] chars = str.toCharArray();
            char[] invalidPattern = {'b', 'a'};
            steps = 0L;

            for (int i = 0; i < chars.length - 1; i++) {
                steps++;
                if (chars[i] == invalidPattern[0] && chars[i + 1] == invalidPattern[1]) {
                    result = false;
                    break;
                }
            }
            super.callback.accept("pattern matching", result, steps);
        }
    };

     static class OppositeEndsCheck extends ThreadProcess {

         public OppositeEndsCheck(TriConsumer callback, String input) {
             super(callback, input);
         }

         @Override
        public void run() {
            //convert to char array
            char[] chars = str.toCharArray();
            boolean leftCharB = false, rightCharA = false;

            int leftIndex = 0, rightIndex = chars.length - 1;

            while (leftIndex < rightIndex) {
                steps++;
                if (chars[leftIndex] == 'b') {
                    leftCharB = true;
                } else if (leftCharB) {
                    result = false;
                    break;
                }
                if (chars[rightIndex] == 'a') {
                    rightCharA = true;
                } else if (rightCharA) {
                    result = false;
                    break;
                }

                if (leftCharB && rightCharA) {
                    result = false;
                    break;
                }
                leftIndex++;
                rightIndex--;
            }
            super.callback.accept("oppositeEndsCheck", result, steps);
        }
    };

}
