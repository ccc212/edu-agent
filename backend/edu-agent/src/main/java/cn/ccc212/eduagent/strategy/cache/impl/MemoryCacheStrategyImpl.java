package cn.ccc212.eduagent.strategy.cache.impl;

import cn.ccc212.eduagent.strategy.cache.CacheStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MemoryCacheStrategyImpl implements CacheStrategy {

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    /**
     * 内部类，用于存储缓存值和过期时间
     */
    private static class CacheEntry {
        Object value;
        long expiryTime; // System.currentTimeMillis() + timeoutInMillis

        public CacheEntry(Object value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return expiryTime > 0 && System.currentTimeMillis() > expiryTime;
        }
    }

    public MemoryCacheStrategyImpl() {
        // 定时清理过期缓存
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::cleanupExpiredEntries, 1, 1, TimeUnit.MINUTES);
    }

    private void cleanupExpiredEntries() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
        log.debug("MemoryCacheStrategyImpl: 已清理所有键值对");
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, new CacheEntry(value, -1)); // -1 表示永不过期
        log.debug("MemoryCacheStrategyImpl: put key={}, value={}", key, value);
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit unit) {
        long expiryTime = System.currentTimeMillis() + unit.toMillis(timeout);
        cache.put(key, new CacheEntry(value, expiryTime));
        log.debug("MemoryCacheStrategyImpl: put key={}, value={}, timeout={} {}", key, value, timeout, unit);
    }

    @Override
    public <T> T get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            if (entry != null && entry.isExpired()) {
                cache.remove(key); // 移除过期项
            }
            log.debug("MemoryCacheStrategyImpl: get key={}, value=null (expired or not found)", key);
            return null;
        }
        log.debug("MemoryCacheStrategyImpl: get key={}, value={}", key, entry.value);
        return (T) entry.value;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        T value = get(key);
        if (value != null && type.isInstance(value)) {
            return type.cast(value);
        }
        log.debug("MemoryCacheStrategyImpl: get key={}, value=null (type mismatch)", key);
        return null;
    }

    @Override
    public void delete(String key) {
        cache.remove(key);
        log.debug("MemoryCacheStrategyImpl: delete key={}", key);
    }

    @Override
    public boolean containsKey(String key) {
        CacheEntry entry = cache.get(key);
        boolean contains = entry != null && !entry.isExpired();
        log.debug("MemoryCacheStrategyImpl: containsKey key={}, result={}", key, contains);
        return contains;
    }

    @Override
    public void clear() {
        cache.clear();
        log.debug("MemoryCacheStrategyImpl: 已清理所有键值对");
    }
}