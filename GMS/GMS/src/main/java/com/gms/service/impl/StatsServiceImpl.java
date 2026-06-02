package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gms.pojo.Area;
import com.gms.pojo.MaintenanceRecord;
import com.gms.pojo.PlantSpecies;
import com.gms.pojo.PlantingRecord;
import com.gms.service.*;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private AreaService areaService;

    @Autowired
    private PlantSpeciesService plantSpeciesService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlantingRecordService plantingRecordService;

    @Autowired
    private MaintenanceRecordService maintenanceRecordService;

    @Override
    public Result getDashboardStats() {
        try {
            // 从数据库获取真实数据
            long areaCount = areaService.count();
            long plantSpeciesCount = plantSpeciesService.count();

            // 获取本月时间范围
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

            // 本月养护次数
            QueryWrapper<MaintenanceRecord> maintenanceWrapper = new QueryWrapper<>();
            maintenanceWrapper.between("maintenance_time", startOfMonth, endOfMonth);
            long monthlyMaintenanceCount = maintenanceRecordService.count(maintenanceWrapper);

            // 本月种植次数
            QueryWrapper<PlantingRecord> plantingWrapper = new QueryWrapper<>();
            plantingWrapper.between("planting_time", startOfMonth, endOfMonth);
            long monthlyPlantingCount = plantingRecordService.count(plantingWrapper);

            // 养护人统计
            QueryWrapper<MaintenanceRecord> maintainerWrapper = new QueryWrapper<>();
            maintainerWrapper.select("DISTINCT maintainer");
            long maintainerCount = maintenanceRecordService.count(maintainerWrapper);

            // 种植人统计
            QueryWrapper<PlantingRecord> planterWrapper = new QueryWrapper<>();
            planterWrapper.select("DISTINCT responsible_person");
            long planterCount = plantingRecordService.count(planterWrapper);

            // 总用户数
            long totalUserCount = userService.count();

            // 计算百分比
            double maintainerPercentage = totalUserCount > 0 ? (double) maintainerCount / totalUserCount * 100 : 0;
            double planterPercentage = totalUserCount > 0 ? (double) planterCount / totalUserCount * 100 : 0;

            // 构建统计数据
            Map<String, Object> data = new HashMap<>();
            data.put("areaCount", areaCount);
            data.put("plantSpeciesCount", plantSpeciesCount);
            data.put("monthlyMaintenanceCount", monthlyMaintenanceCount);
            data.put("monthlyPlantingCount", monthlyPlantingCount);
            data.put("maintainerCount", maintainerCount);
            data.put("maintainerPercentage", Math.round(maintainerPercentage * 100.0) / 100.0);
            data.put("planterCount", planterCount);
            data.put("planterPercentage", Math.round(planterPercentage * 100.0) / 100.0);

            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取统计数据失败: " + e.getMessage());
        }
    }

    @Override
    public Result getRecentActivities() {
        try {
            // 获取最近的种植活动（不限时间）
            QueryWrapper<PlantingRecord> plantingWrapper = new QueryWrapper<>();
            plantingWrapper.orderByDesc("planting_time").last("LIMIT 10");

            List<PlantingRecord> plantingRecords = plantingRecordService.list(plantingWrapper);

            // 获取最近的养护活动（不限时间）
            QueryWrapper<MaintenanceRecord> maintenanceWrapper = new QueryWrapper<>();
            maintenanceWrapper.orderByDesc("maintenance_time").last("LIMIT 10");

            List<MaintenanceRecord> maintenanceRecords = maintenanceRecordService.list(maintenanceWrapper);

            // 转换种植活动数据
            List<Map<String, Object>> plantingActivities = plantingRecords.stream().map(record -> {
                Map<String, Object> activity = new HashMap<>();
                activity.put("id", record.getId());
                activity.put("areaId", record.getAreaId());
                activity.put("speciesId", record.getSpeciesId());
                activity.put("responsiblePerson", record.getResponsiblePerson());
                activity.put("plantingTime", record.getPlantingTime());
                activity.put("type", "种植");

                // 获取区块名称
                Area area = areaService.getById(record.getAreaId());
                if (area != null) {
                    activity.put("areaName", area.getAreaName());
                } else {
                    activity.put("areaName", "未知区块");
                }

                // 获取植物品种名称
                PlantSpecies species = plantSpeciesService.getById(record.getSpeciesId());
                if (species != null) {
                    activity.put("speciesName", species.getSpeciesName());
                } else {
                    activity.put("speciesName", "未知品种");
                }

                return activity;
            }).collect(Collectors.toList());

            // 转换养护活动数据
            List<Map<String, Object>> maintenanceActivities = maintenanceRecords.stream().map(record -> {
                Map<String, Object> activity = new HashMap<>();
                activity.put("id", record.getId());
                activity.put("areaId", record.getAreaId());
                activity.put("speciesId", record.getSpeciesId());
                activity.put("maintainer", record.getMaintainer());
                activity.put("maintenanceTime", record.getMaintenanceTime());
                activity.put("type", "养护");

                // 获取区块名称
                Area area = areaService.getById(record.getAreaId());
                if (area != null) {
                    activity.put("areaName", area.getAreaName());
                } else {
                    activity.put("areaName", "未知区块");
                }

                // 获取植物品种名称
                PlantSpecies species = plantSpeciesService.getById(record.getSpeciesId());
                if (species != null) {
                    activity.put("speciesName", species.getSpeciesName());
                } else {
                    activity.put("speciesName", "未知品种");
                }

                return activity;
            }).collect(Collectors.toList());

            // 构建响应数据
            Map<String, Object> data = new HashMap<>();
            data.put("plantingActivities", plantingActivities);
            data.put("maintenanceActivities", maintenanceActivities);

            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取近日活动失败: " + e.getMessage());
        }
    }

    @Override
    public Result getPlantDistribution() {
        try {
            // 查询所有区块
            List<Area> areas = areaService.list();

            // 查询种植记录，按区块分组统计
            List<PlantingRecord> allPlantingRecords = plantingRecordService.list();

            // 按区块ID分组
            Map<String, List<PlantingRecord>> recordsByArea = allPlantingRecords.stream()
                    .collect(Collectors.groupingBy(PlantingRecord::getAreaId));

            // 构建植物分布数据
            List<Map<String, Object>> distributionData = areas.stream().map(area -> {
                Map<String, Object> distribution = new HashMap<>();
                distribution.put("areaId", area.getAreaId());
                distribution.put("areaName", area.getAreaName());

                List<PlantingRecord> areaRecords = recordsByArea.get(area.getAreaId());
                if (areaRecords != null && !areaRecords.isEmpty()) {
                    // 统计植物种类数（去重的speciesId）
                    long speciesCount = areaRecords.stream()
                            .map(PlantingRecord::getSpeciesId)
                            .distinct()
                            .count();

                    // 统计总株数
                    int plantTotalCount = areaRecords.stream()
                            .mapToInt(PlantingRecord::getPlantCount)
                            .sum();

                    distribution.put("speciesCount", speciesCount);
                    distribution.put("plantTotalCount", plantTotalCount);
                } else {
                    distribution.put("speciesCount", 0);
                    distribution.put("plantTotalCount", 0);
                }

                return distribution;
            }).collect(Collectors.toList());

            return Result.success(distributionData);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取植物分布数据失败: " + e.getMessage());
        }
    }
}
