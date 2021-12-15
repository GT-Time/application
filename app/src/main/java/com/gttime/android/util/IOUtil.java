package com.gttime.android.util;

public final class IOUtil {
    private IOUtil() {
        throw new UnsupportedOperationException();
    }

    public static java.lang.String getFileName(java.lang.String filename) {
        return filename + ".json";
    }
}
