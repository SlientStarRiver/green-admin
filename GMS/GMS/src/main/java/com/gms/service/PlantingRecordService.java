package com.gms.service;

import com.gms.pojo.PlantingRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gms.utils.Result;

/**
* @author HP
* @description 针对表【planting_record】的数据库操作Service
* @createDate 2025-11-20 13:10:07
*/
public interface PlantingRecordService extends IService<PlantingRecord> {
    Result getRecordPage(Integer page, Integer size, String speciesId);
}
