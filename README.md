# About
map容器

* FWeakKeyMap key是弱引用的Map
* FWeakValueMap value是弱引用的Map
* FWeakMap key和value是弱引用的Map
* FUniqueMap key和value都是唯一的Map

# Gradle
[![](https://jitpack.io/v/zj565061763/collection-map.svg)](https://jitpack.io/#zj565061763/collection-map)

# 接口
```java
/**
 * 此接口的默认实现类方法参数的key和value都不支持null，如果传null不会抛异常，会失败。
 */
public interface IMap<K, V> {
    V put(K key, V value);

    V remove(Object key);

    V get(Object key);

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    int size();

    void clear();

    void foreach(ForeachCallback<? super K, ? super V> callback);

    Map<K, V> toMap(Map<K, V> map);

    interface ForeachCallback<K, V> {
        boolean onItem(K itemKey, V itemValue);
    }
}
```

```java
/**
 * key和value都是唯一的Map
 */
public interface IUniqueMap<K, V> extends IMap<K, V> {
    /**
     * 根据value移除键值对
     *
     * @return 返回被移除的key
     */
    K removeByValue(Object value);

    /**
     * 根据value查找key
     */
    K getKeyByValue(Object value);
}
```