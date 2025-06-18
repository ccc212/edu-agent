package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class StudentUsageStatsVO {
    
    /**
     * 当日学生使用次数
     */
    private Long dailyUsageCount;

    /**
     * 本周学生使用次数
     */
    private Long weeklyUsageCount;

    /**
     * 当日学生活跃板块 (板块名称 -> 次数)
     */
    private Map<String, Long> dailyActiveSections;

    /**
     * 本周学生活跃板块 (板块名称 -> 次数)
     */
    private Map<String, Long> weeklyActiveSections;
    
}