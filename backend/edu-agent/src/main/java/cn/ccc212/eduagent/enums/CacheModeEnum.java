package cn.ccc212.eduagent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheModeEnum {

    MEMORY("memory", "memoryCacheStrategyImpl"),

    RABBITMQ("redis", "redisCacheStrategyImpl");

    private final String mode;

    private final String strategy;

    public static String getStrategy(String mode) {
        for (CacheModeEnum value : CacheModeEnum.values()) {
            if (value.getMode().equalsIgnoreCase(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
