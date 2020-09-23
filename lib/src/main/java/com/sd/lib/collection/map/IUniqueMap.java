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
     * 根据value移除key
     *
     * @param value
     * @return
     */
    K removeKeyByValue(Object value);

    /**
     * 根据value查找key
     *
     * @param value
     * @return
     */
    K getKeyByValue(Object value);
}