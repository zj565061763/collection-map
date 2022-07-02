package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FWeakMap<K, V> implements IMap<K, V> {
    private final ReferenceQueue<K> mQueueKey = new ReferenceQueue<>();
    private final ReferenceQueue<V> mQueueValue = new ReferenceQueue<>();
    private final Map<KeyRef<K>, ValueRef<V>> mMap;

    public FWeakMap() {
        this(new HashMap<>());
    }

    public FWeakMap(Map<KeyRef<K>, ValueRef<V>> map) {
        mMap = map;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;

        releaseReference();

        final KeyRef<K> keyRef = new KeyRef(key, mQueueKey);
        final ValueRef<V> valueRef = new ValueRef(keyRef, value, mQueueValue);
        final ValueRef<V> ref = mMap.put(keyRef, valueRef);
        return ref == null ? null : ref.get();
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;

        releaseReference();

        final KeyRef<K> keyRef = new KeyRef(key, mQueueKey);
        final ValueRef<V> ref = mMap.remove(keyRef);
        return ref == null ? null : ref.get();
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;

        releaseReference();

        final KeyRef<K> keyRef = new KeyRef(key, mQueueKey);
        final ValueRef<V> ref = mMap.get(keyRef);
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
        for (Map.Entry<KeyRef<K>, ValueRef<V>> item : mMap.entrySet()) {
            final K key = item.getKey().get();
            final V value = item.getValue().get();
            if (key != null && value != null) {
                if (callback.onItem(key, value)) break;
            }
        }
    }

    @Override
    public Map<K, V> toMap(Map<K, V> map) {
        releaseReference();
        if (map == null) {
            map = new HashMap<>();
        }

        for (Map.Entry<KeyRef<K>, ValueRef<V>> item : mMap.entrySet()) {
            final K key = item.getKey().get();
            final V value = item.getValue().get();
            if (key != null && value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    private void releaseReference() {
        while (true) {
            final Reference<? extends K> reference = mQueueKey.poll();
            if (reference == null) break;

            mMap.remove(reference);
        }

        while (true) {
            final Reference<? extends V> reference = mQueueValue.poll();
            if (reference == null) break;

            final ValueRef<V> ref = ((ValueRef<V>) reference);
            mMap.remove(ref.mKey);
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
            final FWeakKeyMap.KeyRef other = (FWeakKeyMap.KeyRef) obj;
            return Objects.equals(get(), other.get());
        }
    }

    public static final class ValueRef<T> extends WeakReference<T> {
        private final Object mKey;

        private ValueRef(Object key, T referent, ReferenceQueue<? super T> q) {
            super(referent, q);
            mKey = key;
        }
    }
}
