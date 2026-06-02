package com.gms.controller;

import com.gms.pojo.User;
import com.gms.service.UserService;
import com.gms.service.OperationLogService;
import com.gms.utils.JwtHelper;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping
    public Result getUserPage(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer size) {
        if (page <= 0) {
            return Result.error("页码必须大于0");
        }
        if (size <= 0 || size > 100) {
            return Result.error("每页数量必须在1-100之间");
        }
        return userService.getUserPage(page, size);
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return Result.error("用户ID无效");
        }
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public Result updateUser(@PathVariable Long id, @RequestBody User user, HttpServletRequest request) {
        if (id == null || id <= 0) {
            return Result.error("用户ID无效");
        }
        String currentUser = getCurrentUser(request);
        Result result = userService.updateUser(id, user);
        if (result.getMessage().equals("更新成功")) {
            operationLogService.log(null, currentUser, "更新用户", "用户管理",
                "更新用户ID: " + id, request.getRemoteAddr());
        }
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            return Result.error("用户ID无效");
        }
        String currentUser = getCurrentUser(request);
        Result result = userService.deleteUser(id);
        if (result.getMessage().equals("删除成功")) {
            operationLogService.log(null, currentUser, "删除用户", "用户管理",
                "删除用户ID: " + id, request.getRemoteAddr());
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
