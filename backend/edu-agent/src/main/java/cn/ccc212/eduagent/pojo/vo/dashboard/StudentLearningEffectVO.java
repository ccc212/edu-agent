package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class StudentLearningEffectVO {

    /**
     * 平均正确率趋势
     */
    private List<AverageAccuracyTrend> averageAccuracyTrend;

    /**
     * 知识点掌握情况 (知识点名称 -> 掌握程度百分比)
     */
    private Map<String, BigDecimal> knowledgePointMastery;

    /**
     * 高频错误知识点列表
     */
    private List<String> highFrequencyErrorKnowledgePoints;

}