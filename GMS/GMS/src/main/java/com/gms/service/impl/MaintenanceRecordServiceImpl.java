package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.MaintenanceRecord;
import com.gms.service.MaintenanceRecordService;
import com.gms.mapper.MaintenanceRecordMapper;
import com.gms.utils.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author HP
* @description 针对表【maintenance_record】的数据库操作Service实现
* @createDate 2025-11-20 13:10:01
*/
@Service
public class MaintenanceRecordServiceImpl extends ServiceImpl<MaintenanceRecordMapper, MaintenanceRecord>
    implements MaintenanceRecordService{
    @Override
    public Result getRecordPage(Integer page, Integer size, String speciesId) {
        try {
            // 1. 创建分页对象
            Page<MaintenanceRecord> pageParam = new Page<>(page, size);

            // 2. 构建查询条件
            QueryWrapper<MaintenanceRecord> queryWrapper = new QueryWrapper<>();
            if (speciesId != null && !speciesId.isEmpty()) {
                queryWrapper.eq("species_id", speciesId);
            }

            // 3. 执行分页查询
            Page<MaintenanceRecord> recordPage = this.page(pageParam, queryWrapper);

            // 4. 构建分页响应数据
            Map<String, Object> data = new HashMap<>();
            data.put("records", recordPage.getRecords());
            data.put("currentPage", recordPage.getCurrent());
            data.put("totalPages", recordPage.getPages());
            data.put("hasPrevious", recordPage.hasPrevious());
            data.put("hasNext", recordPage.hasNext());

            // 5. 返回统一结果
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取养护记录列表失败: " + e.getMessage());
        }
    }
}




