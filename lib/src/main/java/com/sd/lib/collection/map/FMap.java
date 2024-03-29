package com.sd.lib.collection.map;

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
    public boolean containsValue(Object value) {
        if (value == null) return false;
        return mMap.containsValue(value);
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
            // 外部可能对map进行了修改，所以这里要检查null
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
            public boolean onItem(K itemKey, V itemValue) {
                map.put(itemKey, itemValue);
                return false;
            }
        });
        return result;
    }
}
