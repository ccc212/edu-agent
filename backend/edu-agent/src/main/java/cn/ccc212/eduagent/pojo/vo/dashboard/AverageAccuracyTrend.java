package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class AverageAccuracyTrend {

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 平均正确率
     */
    private BigDecimal averageAccuracy;

}