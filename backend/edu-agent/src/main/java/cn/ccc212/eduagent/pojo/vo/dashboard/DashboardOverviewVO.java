package cn.ccc212.eduagent.pojo.vo.dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DashboardOverviewVO {

    /**
     * 教师使用统计
     */
    private TeacherUsageStatsVO teacherUsageStats;

    /**
     * 学生使用统计
     */
    private StudentUsageStatsVO studentUsageStats;

    /**
     * 教学效率指数
     */
    private TeachingEfficiencyVO teachingEfficiency;

    /**
     * 学生学习效果
     */
    private StudentLearningEffectVO studentLearningEffect;

}