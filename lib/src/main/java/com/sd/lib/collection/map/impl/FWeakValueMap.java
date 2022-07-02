package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * value是弱引用的Map
 */
public class FWeakValueMap<K, V> implements IMap<K, V> {
    private final ReferenceQueue<V> mQueue = new ReferenceQueue<>();
    private final Map<K, ValueRef<V>> mMap;

    public FWeakValueMap() {
        this(new HashMap<>());
    }

    public FWeakValueMap(Map<K, ValueRef<V>> map) {
        mMap = map;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;

        releaseReference();

        final ValueRef<V> newRef = new ValueRef(key, value, mQueue);
        final ValueRef<V> ref = mMap.put(key, newRef);
        return ref == null ? null : ref.get();
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;

        releaseReference();

        final ValueRef<V> ref = mMap.remove(key);
        return ref == null ? null : ref.get();
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;

        releaseReference();

        final ValueRef<V> ref = mMap.get(key);
        return ref == null ? null : ref.get();
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        releaseReference();
        return mMap.size();
    }

    @Override
    public void clear() {
        mMap.clear();
    }

    @Override
    public void foreach(ForeachCallback<? super K, ? super V> callback) {
        releaseReference();
        for (Map.Entry<K, ValueRef<V>> item : mMap.entrySet()) {
            final V value = item.getValue().get();
            if (value != null) {
                if (callback.onItem(item.getKey(), value)) break;
            }
        }
    }

    @Override
    public Map<K, V> toMap(Map<K, V> map) {
        releaseReference();
        if (map == null) {
            map = new HashMap<>();
        }

        for (Map.Entry<K, ValueRef<V>> item : mMap.entrySet()) {
            final V value = item.getValue().get();
            if (value != null) {
                map.put(item.getKey(), value);
            }
        }
        return map;
    }

    private void releaseReference() {
        while (true) {
            final Reference<? extends V> reference = mQueue.poll();
            if (reference == null) break;

            final ValueRef<V> ref = ((ValueRef<V>) reference);
            final Object key = ref.mKey.get();
            if (key != null) {
                mMap.remove(key);
            }
        }
    }

    public static final class ValueRef<T> extends WeakReference<T> {
        private final WeakReference mKey;

        private ValueRef(Object key, T referent, ReferenceQueue<? super T> q) {
            super(referent, q);
            mKey = new WeakReference(key);
        }
    }
}
