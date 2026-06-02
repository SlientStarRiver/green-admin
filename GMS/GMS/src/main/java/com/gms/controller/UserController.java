package com.gms.controller;

import com.gms.pojo.User;
import com.gms.service.UserService;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表（分页）
     * URL: /admin/users
     * 方法: GET
     */
    @GetMapping
    public Result getUserPage(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer size) {
        // 参数验证
        if (page <= 0) {
            return Result.error("页码必须大于0");
        }
        if (size <= 0 || size > 100) {
            return Result.error("每页数量必须在1-100之间");
        }

        return userService.getUserPage(page, size);
    }

    /**
     * 获取用户详情
     * URL: /admin/users/{id}
     * 方法: GET
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return Result.error("用户ID无效");
        }

        return userService.getUserById(id);
    }

    /**
     * 更新用户信息
     * URL: /admin/users/{id}
     * 方法: PUT
     */
    @PutMapping("/{id}")
    public Result updateUser(@PathVariable Long id, @RequestBody User user) {
        if (id == null || id <= 0) {
            return Result.error("用户ID无效");
        }

        return userService.updateUser(id, user);
    }

    /**
     * 删除用户
     * URL: /admin/users/{id}
     * 方法: DELETE
     */
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return Result.error("用户ID无效");
        }

        return userService.deleteUser(id);
    }
}