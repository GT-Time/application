package com.gttime.android.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapArray<K, V> extends AbstractMap<K,V> {
    private Map<K,V> map;
    private Set<? extends java.util.Map.Entry<K, V>> entries = null;

    public MapArray() {
        this.map = new HashMap<K,V>();
    }

    public MapArray(int size) {
        this.map = new HashMap<K,V>(size);
    }

    public MapArray(K[] keys, V[] values) {
        this.map = new HashMap<K,V>();
        map.putAll(ArrayUtil.toMap(keys, values));
    }

    @NonNull
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        if (entries == null) {
            entries = new AbstractSet<Entry<K, V>>() {

                @Override
                public void clear() {
                    throw new UnsupportedOperationException();
                }

                @NonNull
                @Override
                public Iterator<Entry<K, V>> iterator() {
                    return map.entrySet().iterator();
                }

                @Override
                public int size() {
                    return map.size();
                }
            };
        }
        return (Set<java.util.Map.Entry<K, V>>) entries;
    }

    @Nullable
    @Override
    public V get(@Nullable Object key) {
        return map.get(key);
    }
}
