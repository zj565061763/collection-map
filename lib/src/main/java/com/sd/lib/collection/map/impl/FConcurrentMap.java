package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FConcurrentMap<K, V> implements IMap<K, V>
{
    private final Map<K, V> mMap = new ConcurrentHashMap<>();

    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null)
            return null;

        return mMap.put(key, value);
    }

    @Override
    public V remove(Object key)
    {
        if (key == null)
            return null;

        return mMap.remove(key);
    }

    @Override
    public V get(Object key)
    {
        if (key == null)
            return null;

        return mMap.get(key);
    }

    @Override
    public boolean containsKey(Object key)
    {
        if (key == null)
            return false;

        return mMap.containsKey(key);
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
        return mMap;
    }
}
