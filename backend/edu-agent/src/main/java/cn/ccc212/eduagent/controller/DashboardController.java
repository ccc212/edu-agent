package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.pojo.vo.dashboard.DashboardOverviewVO;
import cn.ccc212.eduagent.service.IDashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "大屏数据相关接口")
@RequiredArgsConstructor
public class DashboardController {

    private final IDashboardService dashboardService;

    /**
     * 获取大屏概览的所有数据
     */
    @GetMapping("/overview")
    public DashboardOverviewVO getDashboardOverview() {
        return dashboardService.getDashboardOverviewData();
    }
}