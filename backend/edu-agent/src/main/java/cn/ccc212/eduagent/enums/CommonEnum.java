package cn.ccc212.eduagent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonEnum {

    True(true, 1),

    False(false, 0);

    private final boolean bool;

    private final Integer value;

}
