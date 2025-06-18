package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class CourseOptimizationDirection {

    /**
     * 编程主题/知识点领域
     */
    private String programmingTopic;

    /**
     * 优化描述 (如：通过率持续偏低)
     */
    private String description;

    /**
     * 当前通过率
     */
    private BigDecimal passRate;

}