package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * key是弱引用的Map
 *
 * @param <K>
 * @param <V>
 */
public class FWeakKeyMap<K, V> implements IMap<K, V>
{
    private final Map<K, V> mMap = new WeakHashMap<>();

    @Override
    public V put(K key, V value)
    {
        return mMap.put(key, value);
    }

    @Override
    public V remove(Object key)
    {
        return mMap.remove(key);
    }

    @Override
    public V get(Object key)
    {
        return mMap.get(key);
    }

    @Override
    public int size()
    {
        return mMap.size();
    }

    @Override
    public void clear()
    {
        mMap.clear();
    }

    @Override
    public Map<K, V> toMap()
    {
        return new HashMap<>(mMap);
    }
}
