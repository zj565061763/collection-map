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
 *
 * @param <K>
 * @param <V>
 */
public class FWeakValueMap<K, V> implements IMap<K, V>
{
    private final Map<K, WeakReference<V>> mMap = new HashMap<>();
    private final ReferenceQueue<V> mQueue = new ReferenceQueue<>();

    private final Map<WeakReference<V>, K> mMapReference = new HashMap<>();

    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null)
            return null;

        final WeakReference<V> refValue = new WeakReference<V>(value, mQueue);
        final WeakReference<V> oldValue = mMap.put(key, refValue);
        mMapReference.put(refValue, key);

        if (oldValue != null)
        {
            mMapReference.remove(oldValue);
            return oldValue.get();
        } else
        {
            return null;
        }
    }

    @Override
    public V remove(Object key)
    {
        if (key == null)
            return null;

        final WeakReference<V> refValue = mMap.remove(key);
        if (refValue != null)
        {
            mMapReference.remove(refValue);
            return refValue.get();
        } else
        {
            return null;
        }
    }

    @Override
    public V get(Object key)
    {
        if (key == null)
            return null;

        final WeakReference<V> refValue = mMap.get(key);
        if (refValue == null)
            return null;

        final V value = refValue.get();
        if (value == null)
        {
            // 值已经被回收，移除键值对
            mMap.remove(key);
            mMapReference.remove(refValue);
        }

        return value;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return get(key) != null;
    }

    @Override
    public int size()
    {
        releaseReference();
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
        releaseReference();
        final Map<K, V> map = new HashMap<>();

        final Iterator<Map.Entry<K, WeakReference<V>>> it = mMap.entrySet().iterator();
        while (it.hasNext())
        {
            final Map.Entry<K, WeakReference<V>> item = it.next();
            final V value = item.getValue().get();
            if (value != null)
            {
                map.put(item.getKey(), value);
            } else
            {
                it.remove();
                mMapReference.remove(item.getValue());
            }
        }
        return map;
    }

    private void releaseReference()
    {
        while (true)
        {
            final Reference<? extends V> reference = mQueue.poll();
            if (reference == null)
                break;

            final K key = mMapReference.remove(reference);
            if (key == null)
            {
                // 该引用已经被移除
                continue;
            }

            final Reference<? extends V> oldReference = mMap.remove(key);
            if (reference != oldReference)
                throw new RuntimeException("reference != oldReference");
        }
    }
}
