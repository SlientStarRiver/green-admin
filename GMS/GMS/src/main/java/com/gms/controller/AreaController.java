package com.gms.controller;

import com.gms.pojo.Area;
import com.gms.pojo.User;
import com.gms.service.AreaService;
import com.gms.service.UserService;
import com.gms.service.OperationLogService;
import com.gms.utils.JwtHelper;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserService userService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping("/areas")
    public Result getAreaPage(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestParam(required = false) String areaName) {
        if (page <= 0) {
            return Result.error("页码必须大于0");
        }
        if (size <= 0 || size > 100) {
            return Result.error("每页数量必须在1-100之间");
        }
        return areaService.getAreaPage(page, size, areaName);
    }

    @GetMapping("/areas/{areaId}")
    public Result getAreaById(@PathVariable String areaId) {
        if (areaId == null || areaId.trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }
        return areaService.getAreaById(areaId);
    }

    @PostMapping("/admin/areas")
    public Result createArea(@RequestBody Area area, HttpServletRequest request) {
        if (area.getAreaId() == null || area.getAreaId().trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }
        if (area.getAreaName() == null || area.getAreaName().trim().isEmpty()) {
            return Result.error("区块名称不能为空");
        }
        if (area.getAreaSize() == null || area.getAreaSize().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return Result.error("区块面积必须大于0");
        }

        String currentUser = getCurrentUser(request);
        Result result = areaService.createArea(area);
        if (result.getMessage().equals("创建成功")) {
            operationLogService.log(null, currentUser, "新增区块", "区块管理",
                "新增区块: " + area.getAreaName() + " (" + area.getAreaId() + ")", request.getRemoteAddr());
        }
        return result;
    }

    @PutMapping("/admin/areas/{areaId}")
    public Result updateArea(@PathVariable String areaId, @RequestBody Area area, HttpServletRequest request) {
        if (areaId == null || areaId.trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }
        String currentUser = getCurrentUser(request);
        Result result = areaService.updateArea(areaId, area);
        if (result.getMessage().equals("更新成功")) {
            operationLogService.log(null, currentUser, "更新区块", "区块管理",
                "更新区块: " + areaId, request.getRemoteAddr());
        }
        return result;
    }

    @DeleteMapping("/admin/areas/{areaId}")
    public Result deleteArea(@PathVariable String areaId, HttpServletRequest request) {
        if (areaId == null || areaId.trim().isEmpty()) {
            return Result.error("区块ID不能为空");
        }
        String currentUser = getCurrentUser(request);
        Result result = areaService.deleteArea(areaId);
        if (result.getMessage().equals("删除成功")) {
            operationLogService.log(null, currentUser, "删除区块", "区块管理",
                "删除区块: " + areaId, request.getRemoteAddr());
        }
        return result;
    }

    private String getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Long userId = jwtHelper.getUserId(token);
                if (userId != null) {
                    User user = userService.getById(userId);
                    if (user != null) {
                        return user.getUsername();
                    }
                }
            } catch (Exception e) {
                // token无效
            }
        }
        return "unknown";
    }
}
