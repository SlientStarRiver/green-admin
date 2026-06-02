package com.gms.controller;

import com.gms.service.StatsService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    /**
     * 获取首页统计数据
     * URL: /stats/dashboard
     * 方法: GET
     */
    @GetMapping("/dashboard")
    public Result getDashboardStats() {
        return statsService.getDashboardStats();
    }

    /**
     * 获取近日活动
     * URL: /stats/recent-activities
     * 方法: GET
     */
    @GetMapping("/recent-activities")
    public Result getRecentActivities() {
        return statsService.getRecentActivities();
    }

    /**
     * 获取植物分布数据
     * URL: /stats/plant-distribution
     * 方法: GET
     */
    @GetMapping("/plant-distribution")
    public Result getPlantDistribution() {
        return statsService.getPlantDistribution();
    }
}