package com.gms.controller;

import com.gms.pojo.PlantingRecord;
import com.gms.service.PlantingRecordService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planting/records")
public class PlantingRecordController {

    @Autowired
    private PlantingRecordService plantingRecordService;

    /**
     * 获取种植记录列表（分页）
     * URL: /planting/records
     * 方法: GET
     */
    @GetMapping
    public Result getRecordPage(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                @RequestParam(required = false) String speciesId) {
        return plantingRecordService.getRecordPage(page, size, speciesId);
    }

    /**
     * 新增种植记录
     * URL: /planting/records
     * 方法: POST
     */
    @PostMapping
    public Result createRecord(@RequestBody PlantingRecord record) {
        return plantingRecordService.save(record) ? Result.success("创建成功") : Result.error("创建失败");
    }

    /**
     * 更新种植记录
     * URL: /planting/records/{id}
     * 方法: PUT
     */
    @PutMapping("/{id}")
    public Result updateRecord(@PathVariable Long id, @RequestBody PlantingRecord record) {
        record.setId(id);
        return plantingRecordService.updateById(record) ? Result.success("更新成功") : Result.error("更新失败");
    }

    /**
     * 删除种植记录
     * URL: /planting/records/{id}
     * 方法: DELETE
     */
    @DeleteMapping("/{id}")
    public Result deleteRecord(@PathVariable Long id) {
        return plantingRecordService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }
}