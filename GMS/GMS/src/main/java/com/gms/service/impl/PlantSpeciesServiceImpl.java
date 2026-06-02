package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.PlantSpecies;
import com.gms.service.PlantSpeciesService;
import com.gms.mapper.PlantSpeciesMapper;
import com.gms.utils.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author HP
* @description 针对表【plant_species】的数据库操作Service实现
* @createDate 2025-11-20 13:10:04
*/
@Service
public class PlantSpeciesServiceImpl extends ServiceImpl<PlantSpeciesMapper, PlantSpecies>
    implements PlantSpeciesService{
    @Override
    public Result getSpeciesPage(Integer page, Integer size, String speciesName) {
        // 1. 创建分页对象
        Page<PlantSpecies> pageParam = new Page<>(page, size);

        // 2. 构建查询条件
        QueryWrapper<PlantSpecies> queryWrapper = new QueryWrapper<>();
        if (speciesName != null && !speciesName.isEmpty()) {
            queryWrapper.like("species_name", speciesName);
        }

        // 3. 执行分页查询
        Page<PlantSpecies> speciesPage = this.page(pageParam, queryWrapper);

        // 4. 构建分页响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("records", speciesPage.getRecords());
        data.put("currentPage", speciesPage.getCurrent());
        data.put("totalPages", speciesPage.getPages());
        data.put("hasPrevious", speciesPage.hasPrevious());
        data.put("hasNext", speciesPage.hasNext());

        // 5. 返回统一结果
        return Result.success(data);
    }

    @Override
    public boolean createSpecies(PlantSpecies plantSpecies) {
        // 检查品种ID是否已存在
        PlantSpecies existingSpecies = getById(plantSpecies.getSpeciesId());
        if (existingSpecies != null) {
            return false;
        }
        return save(plantSpecies);
    }
}




