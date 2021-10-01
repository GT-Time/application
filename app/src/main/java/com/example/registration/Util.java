package com.example.registration;

/**
 *  Collection of utility function
 */
public class Util {
    public static String[] split(String input, String regex, int limit) {
        String[] pair = input.split(regex,limit);

        String[] returnArr = new String[limit];

        if(pair.length <= 1) {
            for(int i = 0; i < limit; i++) returnArr[i] = "";
            return returnArr;
        }

        return pair;
    }

    public static int parseInt(String strNum) {
        int returnVal = 0;
        if (strNum == null) {
            return returnVal;
        }

        try {
            returnVal = (int) Double.parseDouble(strNum.trim());
        } catch (NumberFormatException nfe) {
            returnVal = 0;
        }

        return returnVal;
    }
}
