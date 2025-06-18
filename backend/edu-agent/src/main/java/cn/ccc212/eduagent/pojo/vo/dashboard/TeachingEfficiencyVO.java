package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TeachingEfficiencyVO {

    /**
     * 课与修正平均耗时 (小时/分钟)
     */
    private BigDecimal prepAndCorrectionTimeAvg;

    /**
     * 课后练习设计与修正平均耗时 (小时/分钟)
     */
    private BigDecimal exerciseDesignAndCorrectionTimeAvg;

    /**
     * 课程优化方向列表
     */
    private List<CourseOptimizationDirection> courseOptimizationDirections;

}