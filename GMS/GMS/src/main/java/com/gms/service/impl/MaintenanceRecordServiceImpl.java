package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.Area;
import com.gms.pojo.MaintenanceRecord;
import com.gms.pojo.PlantSpecies;
import com.gms.service.AreaService;
import com.gms.service.MaintenanceRecordService;
import com.gms.service.PlantSpeciesService;
import com.gms.mapper.MaintenanceRecordMapper;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaintenanceRecordServiceImpl extends ServiceImpl<MaintenanceRecordMapper, MaintenanceRecord>
    implements MaintenanceRecordService{

    @Autowired
    private AreaService areaService;

    @Autowired
    private PlantSpeciesService plantSpeciesService;

    @Override
    public Result getRecordPage(Integer page, Integer size, String speciesId) {
        try {
            Page<MaintenanceRecord> pageParam = new Page<>(page, size);

            QueryWrapper<MaintenanceRecord> queryWrapper = new QueryWrapper<>();
            if (speciesId != null && !speciesId.isEmpty()) {
                queryWrapper.eq("species_id", speciesId);
            }
            queryWrapper.orderByDesc("created_time");

            Page<MaintenanceRecord> recordPage = this.page(pageParam, queryWrapper);

            // 关联查询区块名和品种名
            Set<String> areaIds = recordPage.getRecords().stream()
                .map(MaintenanceRecord::getAreaId).collect(Collectors.toSet());
            Set<String> speciesIds = recordPage.getRecords().stream()
                .map(MaintenanceRecord::getSpeciesId).collect(Collectors.toSet());

            Map<String, String> areaNameMap = new HashMap<>();
            Map<String, String> speciesNameMap = new HashMap<>();

            for (String id : areaIds) {
                Area area = areaService.getById(id);
                if (area != null) areaNameMap.put(id, area.getAreaName());
            }
            for (String id : speciesIds) {
                PlantSpecies sp = plantSpeciesService.getById(id);
                if (sp != null) speciesNameMap.put(id, sp.getSpeciesName());
            }

            List<Map<String, Object>> records = recordPage.getRecords().stream().map(r -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", r.getId());
                map.put("areaId", r.getAreaId());
                map.put("areaName", areaNameMap.getOrDefault(r.getAreaId(), ""));
                map.put("speciesId", r.getSpeciesId());
                map.put("speciesName", speciesNameMap.getOrDefault(r.getSpeciesId(), ""));
                map.put("maintenanceTime", r.getMaintenanceTime());
                map.put("plantCount", r.getPlantCount());
                map.put("maintainer", r.getMaintainer());
                map.put("description", r.getDescription());
                map.put("createdTime", r.getCreatedTime());
                return map;
            }).collect(Collectors.toList());

            Map<String, Object> data = new HashMap<>();
            data.put("records", records);
            data.put("currentPage", recordPage.getCurrent());
            data.put("totalPages", recordPage.getPages());
            data.put("hasPrevious", recordPage.hasPrevious());
            data.put("hasNext", recordPage.hasNext());

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取养护记录列表失败: " + e.getMessage());
        }
    }
}




