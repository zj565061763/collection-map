package com.sd.lib.collection.map.impl;

public class FUniqueMap<K, V> extends BaseUniqueMap<K, V>
{
    public FUniqueMap()
    {
        super(new FConcurrentMap<K, V>(), new FConcurrentMap<V, K>());
    }
}
