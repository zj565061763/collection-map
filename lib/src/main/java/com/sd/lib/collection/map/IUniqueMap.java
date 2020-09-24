package com.sd.lib.collection.map;

/**
 * key和value都是唯一的Map
 *
 * @param <K>
 * @param <V>
 */
public interface IUniqueMap<K, V> extends IMap<K, V>
{
    /**
     * 根据value移除键值对
     *
     * @param value
     * @return
     */
    K removeByValue(Object value);

    /**
     * 根据value查找key
     *
     * @param value
     * @return
     */
    K getKeyByValue(Object value);
}