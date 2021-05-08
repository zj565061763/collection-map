package com.sd.lib.collection.map;

/**
 * key和value都是唯一的Map
 */
public interface IUniqueMap<K, V> extends IMap<K, V>
{
    /**
     * 根据value移除键值对
     */
    K removeByValue(Object value);

    /**
     * 根据value查找key
     */
    K getKeyByValue(Object value);
}