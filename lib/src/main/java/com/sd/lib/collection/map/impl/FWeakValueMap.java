package com.sd.lib.collection.map.impl;

import com.sd.lib.collection.map.IMap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * value是弱引用的Map
 *
 * @param <K>
 * @param <V>
 */
public class FWeakValueMap<K, V> implements IMap<K, V>
{
    private final Map<K, WeakReference<V>> mMap = new ConcurrentHashMap<>();
    private final ReferenceQueue<V> mReferenceQueue = new ReferenceQueue<>();

    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null)
            return null;

        final WeakReference<V> preValue = mMap.put(key, new WeakReference<V>(value, mReferenceQueue));
        if (preValue == null)
            return null;

        return preValue.get();
    }

    @Override
    public V remove(Object key)
    {
        if (key == null)
            return null;

        final WeakReference<V> preValue = mMap.remove(key);
        if (preValue == null)
            return null;

        return preValue.get();
    }

    @Override
    public V get(Object key)
    {
        if (key == null)
            return null;

        final WeakReference<V> refValue = mMap.get(key);
        if (refValue == null)
        {
            mMap.remove(key);
            return null;
        }

        return refValue.get();
    }

    @Override
    public boolean containsKey(Object key)
    {
        if (key == null)
            return false;

        releaseReference();
        return mMap.containsKey(key);
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
            final K key = item.getKey();
            final V value = item.getValue().get();
            if (value != null)
            {
                map.put(key, value);
            } else
            {
                it.remove();
            }
        }
        return map;
    }

    private void releaseReference()
    {
        while (true)
        {
            final Reference<? extends V> reference = mReferenceQueue.poll();
            if (reference == null)
                break;

            final Iterator<Map.Entry<K, WeakReference<V>>> it = mMap.entrySet().iterator();
            while (it.hasNext())
            {
                final Map.Entry<K, WeakReference<V>> item = it.next();
                if (reference.equals(item.getValue()))
                {
                    it.remove();
                    break;
                }
            }
        }
    }
}
