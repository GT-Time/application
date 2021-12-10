package com.gttime.android.util;

public final class IntegerUtil {
    private IntegerUtil() {
        throw new UnsupportedOperationException();
    }

    public static int parseInt(java.lang.String strNum) {
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
