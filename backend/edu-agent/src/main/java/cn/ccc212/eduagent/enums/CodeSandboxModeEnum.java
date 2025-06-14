package cn.ccc212.eduagent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeSandboxModeEnum {

    EXAMPLE("example", "exampleCodeSandboxStrategeImpl"),

    REMOTE("remote", "remoteCodeSandboxStrategeImpl"),

    THIRD_PARTY("thirdParty", "thirdPartyCodeSandboxStrategeImpl");

    private final String mode;

    private final String strategy;

    public static String getStrategy(String mode) {
        for (CodeSandboxModeEnum value : CodeSandboxModeEnum.values()) {
            if (value.getMode().equalsIgnoreCase(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
