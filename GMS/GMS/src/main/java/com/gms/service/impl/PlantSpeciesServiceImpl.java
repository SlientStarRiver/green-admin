package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.PlantSpecies;
import com.gms.service.PlantSpeciesService;
import com.gms.service.OperationLogService;
import com.gms.mapper.PlantSpeciesMapper;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlantSpeciesServiceImpl extends ServiceImpl<PlantSpeciesMapper, PlantSpecies>
    implements PlantSpeciesService{

    @Autowired
    private OperationLogService operationLogService;

    @Override
    public Result getSpeciesPage(Integer page, Integer size, String speciesName) {
        Page<PlantSpecies> pageParam = new Page<>(page, size);
        QueryWrapper<PlantSpecies> queryWrapper = new QueryWrapper<>();
        if (speciesName != null && !speciesName.isEmpty()) {
            queryWrapper.like("species_name", speciesName);
        }
        Page<PlantSpecies> speciesPage = this.page(pageParam, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("records", speciesPage.getRecords());
        data.put("currentPage", speciesPage.getCurrent());
        data.put("totalPages", speciesPage.getPages());
        data.put("hasPrevious", speciesPage.hasPrevious());
        data.put("hasNext", speciesPage.hasNext());

        return Result.success(data);
    }

    @Override
    public boolean createSpecies(PlantSpecies plantSpecies) {
        PlantSpecies existingSpecies = getById(plantSpecies.getSpeciesId());
        if (existingSpecies != null) {
            return false;
        }
        boolean success = save(plantSpecies);
        if (success) {
            operationLogService.log(null, "system", "新增品种", "植物管理",
                "新增品种: " + plantSpecies.getSpeciesName() + " (" + plantSpecies.getSpeciesId() + ")", null);
        }
        return success;
    }
}
