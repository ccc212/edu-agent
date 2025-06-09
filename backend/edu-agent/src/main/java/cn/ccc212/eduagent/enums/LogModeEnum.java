package cn.ccc212.eduagent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogModeEnum {

    MULTI_THREAD("multi-thread", "multiThreadLogStrategyImpl"),

    RABBITMQ("rabbitmq", "rabbitMqLogStrategyImpl");

    private final String mode;

    private final String strategy;

    public static String getStrategy(String mode) {
        for (LogModeEnum value : LogModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
