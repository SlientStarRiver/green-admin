package com.gms.service;

import com.gms.pojo.Area;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gms.utils.Result;

/**
* @author HP
* @description 针对表【area】的数据库操作Service
* @createDate 2025-11-20 13:09:55
*/
public interface AreaService extends IService<Area> {
    // 获取区块列表（分页）
    Result getAreaPage(Integer page, Integer size, String areaName);

    // 获取区块详情
    Result getAreaById(String areaId);

    // 新增区块
    Result createArea(Area area);

    // 更新区块信息
    Result updateArea(String areaId, Area area);

    // 删除区块
    Result deleteArea(String areaId);
}
