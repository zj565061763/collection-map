# About
map容器

* FWeakKeyMap key是弱引用的Map
* FWeakValueMap value是弱引用的Map
* FUniqueMap key和value都是唯一的Map
* FWeakKeyUniqueMap key是弱引用，并且key和value都是唯一的Map
* FWeakValueUniqueMap value是弱引用，并且key和value都是唯一的Map

# Gradle
[![](https://jitpack.io/v/zj565061763/collection-map.svg)](https://jitpack.io/#zj565061763/collection-map)

# 接口
```java
public interface IMap<K, V>
{
    V put(K key, V value);

    V remove(Object key);

    V get(Object key);

    boolean containsKey(Object key);

    int size();

    void clear();

    Map<K, V> toMap();
}
```

```java
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
```