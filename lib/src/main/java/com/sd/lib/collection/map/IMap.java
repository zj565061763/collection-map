package com.sd.lib.collection.map;

import java.util.Map;

public interface IMap<K, V>
{
    V put(K key, V value);

    V remove(Object key);

    V get(Object key);

    int size();

    void clear();

    Map<K, V> toMap();
}
