package com.sd.lib.collection.map;

import java.util.Map;

/**
 * key和value都是唯一的Map
 */
public class FUniqueMap<K, V> implements IUniqueMap<K, V> {
    private final IMap<K, V> mMap;
    private final IMap<V, K> mMapReverse = new FMap<>();

    public FUniqueMap() {
        this(new FMap<>());
    }

    public FUniqueMap(IMap<K, V> map) {
        mMap = map;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;

        V resultValue = null;

        final K oldKey = mMapReverse.put(value, key);
        if (oldKey != null) {
            resultValue = mMap.remove(oldKey);
        }

        final V oldValue = mMap.put(key, value);
        if (oldValue != null) {
            mMapReverse.remove(oldValue);
            resultValue = oldValue;
        }

        return resultValue;
    }

    @Override
    public V remove(Object key) {
        final V value = mMap.remove(key);
        if (value == null) return null;

        mMapReverse.remove(value);
        return value;
    }

    @Override
    public V get(Object key) {
        return mMap.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return mMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mMapReverse.containsKey(value);
    }

    @Override
    public int size() {
        return mMap.size();
    }

    @Override
    public void clear() {
        mMap.clear();
        mMapReverse.clear();
    }

    @Override
    public void foreach(ForeachCallback<? super K, ? super V> callback) {
        mMap.foreach(callback);
    }

    @Override
    public Map<K, V> toMap(Map<K, V> map) {
        return mMap.toMap(map);
    }

    @Override
    public K removeByValue(Object value) {
        final K key = mMapReverse.remove(value);
        if (key == null) return null;

        mMap.remove(key);
        return key;
    }

    @Override
    public K getKeyByValue(Object value) {
        return mMapReverse.get(value);
    }
}
