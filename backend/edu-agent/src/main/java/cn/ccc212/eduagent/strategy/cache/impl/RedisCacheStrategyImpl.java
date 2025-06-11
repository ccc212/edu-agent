package cn.ccc212.eduagent.strategy.cache.impl;

import cn.ccc212.eduagent.strategy.cache.CacheStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCacheStrategyImpl implements CacheStrategy {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        log.debug("RedisCacheStrategyImpl: put key={}, value={}", key, value);
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
        log.debug("RedisCacheStrategyImpl: put key={}, value={}, timeout={} {}", key, value, timeout, unit);
    }

    @Override
    public <T> T get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        log.debug("RedisCacheStrategyImpl: get key={}, value={}", key, value);
        return (T) value;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        log.debug("RedisCacheStrategyImpl: get key={}, value={}, type={}", key, value, type.getName());
        if (value != null && type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
        log.debug("RedisCacheStrategyImpl: delete key={}", key);
    }

    @Override
    public boolean containsKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        log.debug("RedisCacheStrategyImpl: containsKey key={}, result={}", key, hasKey);
        return Boolean.TRUE.equals(hasKey);
    }

    @Override
    public void clear() {
        log.warn("RedisCacheStrategyImpl: 已清理所有键值对");
        redisTemplate.delete("edu-agent:*");
    }
}