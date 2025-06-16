package cn.ccc212.eduagent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AIModeEnum {

    ZHIPU("zhipu", "zhipuAIStrategyImpl");

    private final String mode;

    private final String strategy;

    public static String getStrategy(String mode) {
        for (AIModeEnum value : AIModeEnum.values()) {
            if (value.getMode().equalsIgnoreCase(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
