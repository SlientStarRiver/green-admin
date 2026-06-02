package com.gms.controller;

import com.gms.pojo.MaintenanceRecord;
import com.gms.service.MaintenanceRecordService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance/records")
public class MaintenanceRecordController {

    @Autowired
    private MaintenanceRecordService maintenanceRecordService;

    /**
     * 获取养护记录列表（分页）
     * URL: /maintenance/records
     * 方法: GET
     */
    @GetMapping
    public Result getRecordPage(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                @RequestParam(required = false) String speciesId) {
        // 参数验证
        if (page <= 0) {
            return Result.error("页码必须大于0");
        }
        if (size <= 0 || size > 100) {
            return Result.error("每页数量必须在1-100之间");
        }

        return maintenanceRecordService.getRecordPage(page, size, speciesId);
    }

    /**
     * 新增养护记录
     * URL: /maintenance/records
     * 方法: POST
     */
    @PostMapping
    public Result createRecord(@RequestBody MaintenanceRecord record) {
        // 参数验证
        if (record.getAreaId() == null || record.getAreaId().trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }
        if (record.getSpeciesId() == null || record.getSpeciesId().trim().isEmpty()) {
            return Result.error("品种ID不能为空");
        }
        if (record.getMaintenanceTime() == null) {
            return Result.error("养护时间不能为空");
        }
        if (record.getPlantCount() == null || record.getPlantCount() <= 0) {
            return Result.error("株数必须大于0");
        }
        if (record.getMaintainer() == null || record.getMaintainer().trim().isEmpty()) {
            return Result.error("养护人不能为空");
        }

        boolean success = maintenanceRecordService.save(record);
        return success ? Result.success("创建成功") : Result.error("创建失败");
    }

    /**
     * 更新养护记录
     * URL: /maintenance/records/{id}
     * 方法: PUT
     */
    @PutMapping("/{id}")
    public Result updateRecord(@PathVariable Long id, @RequestBody MaintenanceRecord record) {
        if (id == null || id <= 0) {
            return Result.error("记录ID无效");
        }

        record.setId(id);
        boolean success = maintenanceRecordService.updateById(record);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    /**
     * 删除养护记录
     * URL: /maintenance/records/{id}
     * 方法: DELETE
     */
    @DeleteMapping("/{id}")
    public Result deleteRecord(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return Result.error("记录ID无效");
        }

        boolean success = maintenanceRecordService.removeById(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}