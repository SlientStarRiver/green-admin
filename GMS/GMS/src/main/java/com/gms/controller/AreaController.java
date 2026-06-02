package com.gms.controller;

import com.gms.pojo.Area;
import com.gms.service.AreaService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 获取区块列表（分页）
     * URL: /areas
     * 方法: GET
     */
    @GetMapping("/areas")
    public Result getAreaPage(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestParam(required = false) String areaName) {
        // 参数验证
        if (page <= 0) {
            return Result.error("页码必须大于0");
        }
        if (size <= 0 || size > 100) {
            return Result.error("每页数量必须在1-100之间");
        }

        return areaService.getAreaPage(page, size, areaName);
    }

    /**
     * 获取区块详情
     * URL: /areas/{areaId}
     * 方法: GET
     */
    @GetMapping("/areas/{areaId}")
    public Result getAreaById(@PathVariable String areaId) {
        if (areaId == null || areaId.trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }

        return areaService.getAreaById(areaId);
    }

    /**
     * 新增区块
     * URL: /admin/areas
     * 方法: POST
     */
    @PostMapping("/admin/areas")
    public Result createArea(@RequestBody Area area) {
        // 参数验证
        if (area.getAreaId() == null || area.getAreaId().trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }
        if (area.getAreaName() == null || area.getAreaName().trim().isEmpty()) {
            return Result.error("区块名称不能为空");
        }
        if (area.getAreaSize() == null || area.getAreaSize().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return Result.error("区块面积必须大于0");
        }

        return areaService.createArea(area);
    }

    /**
     * 更新区块信息
     * URL: /admin/areas/{areaId}
     * 方法: PUT
     */
    @PutMapping("/admin/areas/{areaId}")
    public Result updateArea(@PathVariable String areaId, @RequestBody Area area) {
        if (areaId == null || areaId.trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }

        return areaService.updateArea(areaId, area);
    }

    /**
     * 删除区块
     * URL: /admin/areas/{areaId}
     * 方法: DELETE
     */
    @DeleteMapping("/admin/areas/{areaId}")
    public Result deleteArea(@PathVariable String areaId) {
        if (areaId == null || areaId.trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }

        return areaService.deleteArea(areaId);
    }
}