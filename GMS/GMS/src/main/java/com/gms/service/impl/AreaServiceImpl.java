package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.Area;
import com.gms.service.AreaService;
import com.gms.mapper.AreaMapper;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area>
    implements AreaService{

    @Override
    public Result getAreaPage(Integer page, Integer size, String areaName) {
        try {
            Page<Area> pageParam = new Page<>(page, size);
            QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
            if (areaName != null && !areaName.isEmpty()) {
                queryWrapper.like("area_name", areaName);
            }
            Page<Area> areaPage = this.page(pageParam, queryWrapper);

            Map<String, Object> data = new HashMap<>();
            data.put("records", areaPage.getRecords());
            data.put("currentPage", areaPage.getCurrent());
            data.put("totalPages", areaPage.getPages());
            data.put("hasPrevious", areaPage.hasPrevious());
            data.put("hasNext", areaPage.hasNext());

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取区块列表失败: " + e.getMessage());
        }
    }

    @Override
    public Result getAreaById(String areaId) {
        try {
            Area area = this.getById(areaId);
            if (area != null) {
                return Result.success(area);
            } else {
                return Result.error("区块不存在");
            }
        } catch (Exception e) {
            return Result.error("获取区块详情失败: " + e.getMessage());
        }
    }

    @Override
    public Result createArea(Area area) {
        try {
            Area existingArea = getById(area.getAreaId());
            if (existingArea != null) {
                return Result.error("区块ID已存在");
            }
            if (area.getAreaName() == null || area.getAreaName().trim().isEmpty()) {
                return Result.error("区块名称不能为空");
            }
            if (area.getAreaSize() == null || area.getAreaSize().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                return Result.error("区块面积必须大于0");
            }

            boolean success = save(area);
            return success ? Result.success("创建成功") : Result.error("创建失败");
        } catch (Exception e) {
            return Result.error("创建区块失败: " + e.getMessage());
        }
    }

    @Override
    public Result updateArea(String areaId, Area area) {
        try {
            Area existingArea = getById(areaId);
            if (existingArea == null) {
                return Result.error("区块不存在");
            }
            if (area.getAreaName() != null && area.getAreaName().trim().isEmpty()) {
                return Result.error("区块名称不能为空");
            }
            if (area.getAreaSize() != null && area.getAreaSize().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                return Result.error("区块面积必须大于0");
            }

            area.setAreaId(areaId);
            boolean success = updateById(area);
            return success ? Result.success("更新成功") : Result.error("更新失败");
        } catch (Exception e) {
            return Result.error("更新区块失败: " + e.getMessage());
        }
    }

    @Override
    public Result deleteArea(String areaId) {
        try {
            Area existingArea = getById(areaId);
            if (existingArea == null) {
                return Result.error("区块不存在");
            }

            boolean success = removeById(areaId);
            return success ? Result.success("删除成功") : Result.error("删除失败");
        } catch (Exception e) {
            return Result.error("删除区块失败: " + e.getMessage());
        }
    }
}
