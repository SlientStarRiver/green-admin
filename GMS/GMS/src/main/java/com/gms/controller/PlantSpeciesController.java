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

    @GetMapping("/plants/species")
    public Result getSpeciesPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String speciesName) {
        return plantSpeciesService.getSpeciesPage(page, size, speciesName);
    }

    @PostMapping("/admin/plants/species")
    public Result createSpecies(@RequestBody PlantSpecies plantSpecies) {
        return plantSpeciesService.createSpecies(plantSpecies) ? Result.success("创建成功") : Result.error("品种ID已存在");
    }

    @PutMapping("/admin/plants/species/{speciesId}")
    public Result updateSpecies(@PathVariable String speciesId, @RequestBody PlantSpecies plantSpecies) {
        plantSpecies.setSpeciesId(speciesId);
        return plantSpeciesService.updateById(plantSpecies) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @DeleteMapping("/admin/plants/species/{speciesId}")
    public Result deleteSpecies(@PathVariable String speciesId) {
        return plantSpeciesService.removeById(speciesId) ? Result.success("删除成功") : Result.error("删除失败");
    }
}
