package com.sd.lib.collection.map;

public interface IUniqueValueMap<K, V> extends IMap<K, V>
{
    K removeValue(Object value);

    K getKey(Object value);
}