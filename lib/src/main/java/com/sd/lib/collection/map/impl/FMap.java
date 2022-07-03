package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.util.HashMap;
import java.util.Map;

public class FMap<K, V> implements IMap<K, V> {
    private final Map<K, V> mMap;

    public FMap() {
        this(new HashMap<>());
    }

    public FMap(Map<K, V> map) {
        mMap = map;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;
        return mMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;
        return mMap.remove(key);
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;
        return mMap.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) return false;
        return mMap.containsKey(key);
    }

    @Override
    public int size() {
        return mMap.size();
    }

    @Override
    public void clear() {
        mMap.clear();
    }

    @Override
    public void foreach(ForeachCallback<? super K, ? super V> callback) {
        for (Map.Entry<K, V> item : mMap.entrySet()) {
            final K key = item.getKey();
            final V value = item.getValue();
            if (key != null && value != null) {
                if (callback.onItem(key, value)) break;
            }
        }
    }

    @Override
    public Map<K, V> toMap(Map<K, V> map) {
        final Map<K, V> result = map == null ? new HashMap<>() : map;
        foreach(new ForeachCallback<K, V>() {
            @Override
            public boolean onItem(K key, V value) {
                map.put(key, value);
                return false;
            }
        });
        return result;
    }
}
