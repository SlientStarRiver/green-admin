package com.gms.service;

import com.gms.pojo.PlantSpecies;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gms.utils.Result;

/**
* @author HP
* @description 针对表【plant_species】的数据库操作Service
* @createDate 2025-11-20 13:10:04
*/
public interface PlantSpeciesService extends IService<PlantSpecies> {
    Result getSpeciesPage(Integer page, Integer size, String speciesName);
    boolean createSpecies(PlantSpecies plantSpecies);
}
