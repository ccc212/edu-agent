package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class TeacherUsageStatsVO {

    /**
     * 当日教师使用次数
     */
    private Long dailyUsageCount;

    /**
     * 本周教师使用次数
     */
    private Long weeklyUsageCount;

    /**
     * 当日教师活跃板块 (板块名称 -> 次数)
     */
    private Map<String, Long> dailyActiveSections;

    /**
     * 本周教师活跃板块 (板块名称 -> 次数)
     */
    private Map<String, Long> weeklyActiveSections;

}