package cn.ccc212.eduagent.context;

import cn.ccc212.eduagent.enums.CacheModeEnum;
import cn.ccc212.eduagent.strategy.cache.CacheStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CacheContext {

    @Value("${edu-agent.cache.mode}")
    private String cacheMode;

    private final Map<String, CacheStrategy> cacheStrategyMap;

    private CacheStrategy getCurrentStrategy() {
        String strategyBeanName = CacheModeEnum.getStrategy(cacheMode);
        if (strategyBeanName == null) {
            throw new IllegalStateException("Unknown cache mode: " + cacheMode);
        }

        CacheStrategy strategy = cacheStrategyMap.get(strategyBeanName);
        if (strategy == null) {
            throw new IllegalStateException("Cache strategy bean not found for mode: " + cacheMode + ", bean name: " + strategyBeanName);
        }
        return strategy;
    }

    public void put(String key, Object value) {
        getCurrentStrategy().put(key, value);
    }

    public void put(String key, Object value, long timeout, TimeUnit unit) {
        getCurrentStrategy().put(key, value, timeout, unit);
    }

    public <T> T get(String key) {
        return getCurrentStrategy().get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return getCurrentStrategy().get(key, type);
    }

    public void delete(String key) {
        getCurrentStrategy().delete(key);
    }

    public boolean containsKey(String key) {
        return getCurrentStrategy().containsKey(key);
    }

    public void clear() {
        getCurrentStrategy().clear();
    }
}
