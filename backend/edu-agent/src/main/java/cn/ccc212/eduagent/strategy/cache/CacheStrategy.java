package cn.ccc212.eduagent.strategy.cache;

import java.util.concurrent.TimeUnit;

public interface CacheStrategy {

    /**
     * 将键值对存入缓存
     * @param key 键
     * @param value 值
     */
    void put(String key, Object value);

    /**
     * 将键值对存入缓存，并设置过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    void put(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 从缓存中获取值
     * @param key 键
     * @param <T> 值类型
     * @return 缓存的值，如果不存在则返回 null
     */
    <T> T get(String key);

    /**
     * 从缓存中获取值，并指定类型
     * @param key 键
     * @param type 值类型
     * @param <T> 值类型
     * @return 缓存的值，如果不存在则返回 null
     */
    <T> T get(String key, Class<T> type);

    /**
     * 从缓存中删除键值对
     * @param key 键
     */
    void delete(String key);

    /**
     * 检查缓存中是否存在某个键
     * @param key 键
     * @return true 如果存在，否则 false
     */
    boolean containsKey(String key);

    /**
     * 清空所有缓存
     */
    void clear();
}