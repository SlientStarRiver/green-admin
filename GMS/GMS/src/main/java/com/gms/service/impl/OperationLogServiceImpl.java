package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.OperationLog;
import com.gms.mapper.OperationLogMapper;
import com.gms.service.OperationLogService;
import com.gms.utils.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService {

    @Override
    public Result getLogPage(Integer page, Integer size, String username, String module) {
        try {
            Page<OperationLog> pageParam = new Page<>(page, size);
            QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();

            if (username != null && !username.isEmpty()) {
                queryWrapper.like("username", username);
            }
            if (module != null && !module.isEmpty()) {
                queryWrapper.eq("module", module);
            }
            queryWrapper.orderByDesc("create_time");

            Page<OperationLog> logPage = this.page(pageParam, queryWrapper);

            Map<String, Object> data = new HashMap<>();
            data.put("records", logPage.getRecords());
            data.put("currentPage", logPage.getCurrent());
            data.put("totalPages", logPage.getPages());
            data.put("hasPrevious", logPage.hasPrevious());
            data.put("hasNext", logPage.hasNext());

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取日志列表失败: " + e.getMessage());
        }
    }

    @Override
    public void log(Long userId, String username, String action, String module, String detail, String ip) {
        try {
            OperationLog log = new OperationLog();
            log.setUserId(userId);
            log.setUsername(username);
            log.setAction(action);
            log.setModule(module);
            log.setDetail(detail);
            log.setIp(ip);
            this.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
