package com.gttime.android.util;

import java.util.HashMap;
import java.util.Map;

public final class ArrayUtil {
    public ArrayUtil() {
        throw new UnsupportedOperationException();
    };

    public static <K,V> Map<K,V> toMap(K[] keys, V[] values) {
        if (keys == null || values == null) return null;

        int length = Math.min(keys.length, values.length);

        if(length != keys.length || length != values.length) throw new ArrayIndexOutOfBoundsException("Arrays have different size");

        Map<K,V> map = new HashMap<K,V>();

        for (int i = 0; i < length; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    };
}
