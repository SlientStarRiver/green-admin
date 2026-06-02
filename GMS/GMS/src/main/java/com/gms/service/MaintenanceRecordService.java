package com.gms.service;

import com.gms.pojo.MaintenanceRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gms.utils.Result;

/**
* @author HP
* @description 针对表【maintenance_record】的数据库操作Service
* @createDate 2025-11-20 13:10:01
*/
public interface MaintenanceRecordService extends IService<MaintenanceRecord> {
    Result getRecordPage(Integer page, Integer size, String speciesId);
}
