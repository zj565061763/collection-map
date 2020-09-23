package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IUniqueValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 未考虑多线程问题，如果需要在多线程下使用，请手动同步
 *
 * @param <K>
 * @param <V>
 */
public class FUniqueValueMap<K, V> implements IUniqueValueMap<K, V>
{
    private final Map<K, V> mMap = new ConcurrentHashMap<>();
    private final Map<V, K> mMapReverse = new ConcurrentHashMap<>();

    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null)
            return null;

        final K cacheKey = mMapReverse.get(value);
        if (cacheKey == null)
        {
            // 存储键值对
        } else
        {
            if (key.equals(cacheKey))
            {
                // 已经存储过了
                return null;
            } else
            {
                // 移除旧的键值对，存储新的键值对
                mMap.remove(cacheKey);
            }
        }

        final V result = mMap.put(key, value);
        mMapReverse.put(value, key);
        return result;
    }

    @Override
    public V remove(Object key)
    {
        if (key == null)
            return null;

        final V value = mMap.remove(key);
        if (value == null)
            return null;

        final K cacheKey = mMapReverse.remove(value);
        if (cacheKey == null)
            throw new RuntimeException("Cached key was not found");

        return value;
    }

    @Override
    public V get(Object key)
    {
        if (key == null)
            return null;

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
        mMapReverse.clear();
    }

    @Override
    public Map<K, V> toMap()
    {
        return new HashMap<>(mMap);
    }

    @Override
    public K removeValue(Object value)
    {
        if (value == null)
            return null;

        final K key = mMapReverse.remove(value);
        if (key == null)
            return null;

        final V cacheValue = mMap.remove(key);
        if (cacheValue == null)
            throw new RuntimeException("Cached value was not found");

        return key;
    }

    @Override
    public K getKey(Object value)
    {
        if (value == null)
            return null;

        return mMapReverse.get(value);
    }
}
