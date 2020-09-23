package com.sd.lib.collection.map;

/**
 * 键和值都是唯一的Map
 *
 * @param <K>
 * @param <V>
 */
public interface IUniqueMap<K, V> extends IMap<K, V>
{
    /**
     * 移除某个指定的value
     *
     * @param value
     * @return
     */
    K removeValue(Object value);

    /**
     * 根据value查找key
     *
     * @param value
     * @return
     */
    K getKey(Object value);
}