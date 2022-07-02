package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * key是弱引用的Map
 */
public class FWeakKeyMap<K, V> implements IMap<K, V> {
    private final ReferenceQueue<K> mQueue = new ReferenceQueue<>();
    private final Map<KeyRef<K>, V> mMap;

    public FWeakKeyMap() {
        this(new HashMap<>());
    }

    public FWeakKeyMap(Map<KeyRef<K>, V> map) {
        mMap = map;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;

        releaseReference();

        final KeyRef<K> ref = new KeyRef(key, mQueue);
        return mMap.put(ref, value);
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;

        releaseReference();

        final KeyRef<K> ref = new KeyRef(key, mQueue);
        return mMap.remove(ref);
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;

        releaseReference();

        final KeyRef<K> ref = new KeyRef(key, mQueue);
        return mMap.get(ref);
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
        for (Map.Entry<KeyRef<K>, V> item : mMap.entrySet()) {
            final KeyRef<K> ref = item.getKey();
            final K key = ref.get();
            if (key != null) {
                if (callback.onItem(key, item.getValue())) break;
            }
        }
    }

    @Override
    public Map<K, V> toMap(Map<K, V> map) {
        releaseReference();
        if (map == null) {
            map = new HashMap<>();
        }

        for (Map.Entry<KeyRef<K>, V> item : mMap.entrySet()) {
            final KeyRef<K> ref = item.getKey();
            final K key = ref.get();
            if (key != null) {
                map.put(key, item.getValue());
            }
        }
        return map;
    }

    private void releaseReference() {
        while (true) {
            final Reference<? extends K> reference = mQueue.poll();
            if (reference == null) break;

            mMap.remove(reference);
        }
    }

    public static final class KeyRef<T> extends WeakReference<T> {
        private final int mHashCode;

        private KeyRef(T referent, ReferenceQueue<? super T> q) {
            super(referent, q);
            mHashCode = referent.hashCode();
        }

        @Override
        public int hashCode() {
            return mHashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != getClass()) return false;
            final KeyRef other = (KeyRef) obj;
            return Objects.equals(get(), other.get());
        }
    }
}
