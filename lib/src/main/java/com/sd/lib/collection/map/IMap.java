package com.sd.lib.collection.map;

import java.util.Map;

public interface IMap<K, V> {
    V put(K key, V value);

    V remove(Object key);

    V get(Object key);

    boolean containsKey(Object key);

    int size();

    void clear();

    void foreach(ForeachCallback<? super K, ? super V> callback);

    Map<K, V> toMap(Map<K, V> map);

    interface ForeachCallback<K, V> {
        boolean onItem(K key, V value);
    }
}
