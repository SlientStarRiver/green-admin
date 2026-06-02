package com.gms.service;

import com.gms.utils.Result;


public interface StatsService {
    Result getDashboardStats();
    Result getRecentActivities();
    Result getPlantDistribution();
}
