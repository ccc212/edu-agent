package cn.ccc212.eduagent.service.impl;

import cn.ccc212.eduagent.constant.RoleConstant;
import cn.ccc212.eduagent.mapper.UserMapper;
import cn.ccc212.eduagent.pojo.vo.dashboard.DashboardOverviewVO;
import cn.ccc212.eduagent.pojo.vo.dashboard.StudentUsageStatsVO;
import cn.ccc212.eduagent.pojo.vo.dashboard.TeacherUsageStatsVO;
import cn.ccc212.eduagent.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardImpl implements IDashboardService {

    private final UserMapper userMapper;

    @Override
    public DashboardOverviewVO getDashboardOverviewData() {
        return new DashboardOverviewVO()
                .setTeacherUsageStats(getTeacherUsageStats())
                .setStudentUsageStats(getStudentUsageStats());
    }

    private TeacherUsageStatsVO getTeacherUsageStats() {
        Long teacherDaylyUsageCount = userMapper.getDaylyUsageCount(RoleConstant.TEACHER);
        Long teacherWeeklyUsageCount = userMapper.getWeeklyUsageCount(RoleConstant.TEACHER);
        // TODO 当日和本周教师活跃板块
        return new TeacherUsageStatsVO()
                .setDailyUsageCount(teacherDaylyUsageCount)
                .setWeeklyUsageCount(teacherWeeklyUsageCount)
                .setDailyActiveSections(null)
                .setWeeklyActiveSections(null);
    }
    
    private StudentUsageStatsVO getStudentUsageStats() {
        Long studentDaylyUsageCount = userMapper.getDaylyUsageCount(RoleConstant.STUDENT);
        Long studentWeeklyUsageCount = userMapper.getWeeklyUsageCount(RoleConstant.STUDENT);
        // TODO 当日和本周学生活跃板块
        return new StudentUsageStatsVO()
                .setDailyUsageCount(studentDaylyUsageCount)
                .setWeeklyUsageCount(studentWeeklyUsageCount)
                .setDailyActiveSections(null)
                .setWeeklyActiveSections(null);
    }
}
