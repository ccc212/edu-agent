package cn.ccc212.eduagent.service;


import cn.ccc212.eduagent.pojo.vo.dashboard.DashboardOverviewVO;

public interface IDashboardService {

    /**
     * 获取大屏概览的所有数据
     * @return 包含教师、学生统计、教学效率和学生学习效果的综合数据
     */
    DashboardOverviewVO getDashboardOverviewData();

}