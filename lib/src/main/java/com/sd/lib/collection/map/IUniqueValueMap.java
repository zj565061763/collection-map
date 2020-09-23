package com.sd.lib.collection.map;

/**
 * 值唯一的Map
 *
 * @param <K>
 * @param <V>
 */
public interface IUniqueValueMap<K, V> extends IMap<K, V>
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