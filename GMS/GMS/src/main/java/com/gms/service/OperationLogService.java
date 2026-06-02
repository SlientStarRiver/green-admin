package com.gms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gms.pojo.OperationLog;
import com.gms.utils.Result;

public interface OperationLogService extends IService<OperationLog> {
    Result getLogPage(Integer page, Integer size, String username, String module);
    void log(Long userId, String username, String action, String module, String detail, String ip);
}
