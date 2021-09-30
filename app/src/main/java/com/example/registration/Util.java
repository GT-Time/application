package com.example.registration;

/**
 *  Collection of utility function
 */
public class Util {
    public static String[] split(String input, String regex, int limit) {
        String[] returnVal = input.split(regex,limit);
        if(returnVal.length <= 1) {
            for(int i = 0; i < limit; i++) returnVal[i] = "";
        }

        return returnVal;
    }
}
