package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * value是弱引用的Map
 */
public class FWeakValueMap<K, V> implements IMap<K, V> {
    private final Map<K, WeakValue<V>> mMap = new HashMap<>();
    private final ReferenceQueue<V> mQueue = new ReferenceQueue<>();

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;

        releaseReference();

        final WeakValue<V> newRef = new WeakValue(key, value, mQueue);
        final WeakValue<V> oldRef = mMap.put(key, newRef);
        return oldRef == null ? null : oldRef.get();
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;

        releaseReference();

        final WeakValue<V> ref = mMap.remove(key);
        return ref == null ? null : ref.get();
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;

        releaseReference();

        final WeakValue<V> ref = mMap.get(key);
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
    public Map<K, V> toMap() {
        releaseReference();
        final Map<K, V> map = new HashMap<>();

        final Iterator<Map.Entry<K, WeakValue<V>>> it = mMap.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<K, WeakValue<V>> item = it.next();
            final WeakValue<V> ref = item.getValue();

            final V value = ref.get();
            if (value != null) {
                map.put(item.getKey(), value);
            } else {
                it.remove();
            }
        }
        return map;
    }

    private void releaseReference() {
        while (true) {
            final Reference<? extends V> reference = mQueue.poll();
            if (reference == null) break;

            final WeakValue<V> ref = ((WeakValue<V>) reference);
            mMap.remove(ref.mKey);
        }
    }

    private static class WeakValue<T> extends WeakReference<T> {
        private final Object mKey;

        public WeakValue(Object key, T referent, ReferenceQueue<? super T> q) {
            super(referent, q);
            mKey = key;
        }
    }
}
