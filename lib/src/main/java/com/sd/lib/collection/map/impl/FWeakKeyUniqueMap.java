package com.sd.lib.collection.map.impl;

public class FWeakKeyUniqueMap<K, V> extends BaseUniqueMap<K, V>
{
    public FWeakKeyUniqueMap()
    {
        super(new FWeakKeyMap<K, V>(), new FWeakValueMap<V, K>());
    }
}
