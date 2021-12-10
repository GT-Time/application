package com.gttime.android.util;

public final class StringUtil {
    private StringUtil() {
        throw new UnsupportedOperationException();
    }

    public static String[] split(String input, String regex, int limit) {
        String[] pair = input.split(regex,limit);

        String[] returnArr = new String[limit];

        if(pair.length <= 1) {
            for(int i = 0; i < limit; i++) returnArr[i] = "";
            return returnArr;
        }

        return pair;
    }
}
