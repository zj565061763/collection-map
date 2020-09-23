package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.IUniqueValueMap;

import java.util.Map;

/**
 * 未考虑多线程问题，如果需要在多线程下使用，请手动同步
 *
 * @param <K>
 * @param <V>
 */
public class BaseUniqueValueMap<K, V> implements IUniqueValueMap<K, V>
{
    private final IMap<K, V> mMap;
    private final IMap<V, K> mMapReverse;

    public BaseUniqueValueMap(IMap<K, V> map, IMap<V, K> mapReverse)
    {
        mMap = map;
        mMapReverse = mapReverse;
    }

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
        return mMap.toMap();
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
