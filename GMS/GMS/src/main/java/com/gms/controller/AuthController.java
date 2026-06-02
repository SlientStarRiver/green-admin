package com.gms.controller;

import com.gms.pojo.User;
import com.gms.service.UserService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * URL: /auth/user/login
     * 方法: POST
     */
    @PostMapping("/auth/user/login")
    public Result userlogin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        return userService.userlogin(username, password);
    }
    /**
     * 管理员登录
     * URL: /auth/admin/login
     * 方法: POST
     */
    @PostMapping("/auth/admin/login")
    public Result adminlogin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        return userService.adminlogin(username, password);
    }

    /**
     * 用户注册
     * URL: /auth/register
     * 方法: POST
     */
    @PostMapping("/auth/register")
    public Result register(@RequestBody User user) {
        // 参数验证
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return Result.error("手机号不能为空");
        }

        return userService.register(user);
    }

    /**
     * 用户登出
     * URL: /auth/logout
     * 方法: POST
     */
    @PostMapping("/auth/logout")
    public Result logout() {
        return userService.logout();
    }

    /**
     * 获取当前用户信息
     * URL: /auth/user/info
     * 方法: GET
     */
    @GetMapping("/auth/user/info")
    public Result getUserInfo(@RequestParam Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 修改密码
     * URL: /auth/change-password
     * 方法: POST
     */
    @PostMapping("/auth/change-password")
    public Result changePassword(@RequestBody Map<String, String> data) {
        String userIdStr = data.get("userId");
        String oldPassword = data.get("oldPassword");
        String newPassword = data.get("newPassword");

        if (userIdStr == null || oldPassword == null || newPassword == null) {
            return Result.error("参数不完整");
        }

        try {
            Long userId = Long.parseLong(userIdStr);
            return userService.changePassword(userId, oldPassword, newPassword);
        } catch (NumberFormatException e) {
            return Result.error("用户ID格式错误");
        }
    }
}