package com.gms.controller;

import com.gms.pojo.PlantSpecies;
import com.gms.service.PlantSpeciesService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PlantSpeciesController {

    @Autowired
    private PlantSpeciesService plantSpeciesService;

    /**
     * 获取植物品种列表（分页
     * URL: /plants/species
     * 方法: GET
     */
    @GetMapping("/plants/species")
    public Result getSpeciesPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String speciesName) {
        return plantSpeciesService.getSpeciesPage(page, size, speciesName);
    }

    /**
     * 新增植物品种
     * URL: /admin/plants/species
     * 方法: POST
     */
    @PostMapping("/admin/plants/species")
    public Result createSpecies(@RequestBody PlantSpecies plantSpecies) {
        return plantSpeciesService.createSpecies(plantSpecies) ? Result.success("创建成功") : Result.error("品种ID已存在");
    }
}
