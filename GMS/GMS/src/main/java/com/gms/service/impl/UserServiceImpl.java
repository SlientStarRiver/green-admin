package com.gms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gms.pojo.User;
import com.gms.service.UserService;
import com.gms.mapper.UserMapper;
import com.gms.utils.JwtHelper;
import com.gms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;


import java.util.HashMap;
import java.util.Map;

/**
* @author HP
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-11-20 13:10:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public Result getUserPage(Integer page, Integer size) {
        try {
            Page<User> pageParam = new Page<>(page, size);
            Page<User> userPage = this.page(pageParam);

            // 隐藏所有用户的密码
            userPage.getRecords().forEach(u -> u.setPassword(null));

            Map<String, Object> data = new HashMap<>();
            data.put("records", userPage.getRecords());
            data.put("currentPage", userPage.getCurrent());
            data.put("totalPages", userPage.getPages());
            data.put("hasPrevious", userPage.hasPrevious());
            data.put("hasNext", userPage.hasNext());

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取用户列表失败: " + e.getMessage());
        }
    }

    @Override
    public Result getUserById(Long id) {
        try {
            User user = this.getById(id);
            if (user != null) {
                // 隐藏密码
                user.setPassword(null);
                return Result.success(user);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("获取用户详情失败: " + e.getMessage());
        }
    }

    @Override
    public Result updateUser(Long id, User user) {
        try {
            // 1. 根据ID查询用户
            User existingUser = this.getById(id);
            if (existingUser == null) {
                return Result.error("用户不存在");
            }

            boolean needUpdate = false;

            // 2. 更新用户名（如果请求中包含username字段）
            if (!ObjectUtils.isEmpty(user.getUsername())) {
                // 校验用户名是否重复（排除当前用户）
                LambdaQueryWrapper<User> usernameQuery = new LambdaQueryWrapper<>();
                usernameQuery.eq(User::getUsername, user.getUsername())
                        .ne(User::getId, id);

                if (this.count(usernameQuery) > 0) {
                    return Result.error("用户名已被占用");
                }
                existingUser.setUsername(user.getUsername());
                needUpdate = true;
            }

            // 3. 更新手机号（如果请求中包含phone字段）
            if (!ObjectUtils.isEmpty(user.getPhone())) {
                // 校验手机号是否重复（排除当前用户）
                LambdaQueryWrapper<User> phoneQuery = new LambdaQueryWrapper<>();
                phoneQuery.eq(User::getPhone, user.getPhone())
                        .ne(User::getId, id);

                if (this.count(phoneQuery) > 0) {
                    return Result.error("手机号已被占用");
                }
                existingUser.setPhone(user.getPhone());
                needUpdate = true;
            }

            // 4. 更新角色（如果请求中包含role字段）
            if (!ObjectUtils.isEmpty(user.getRole())) {
                existingUser.setRole(user.getRole());
                needUpdate = true;
            }

            // 5. 更新维护次数（如果请求中包含maintenanceCount字段）
            if (user.getMaintenanceCount() != null) {
                existingUser.setMaintenanceCount(user.getMaintenanceCount());
                needUpdate = true;
            }

            // 6. 更新种植次数（如果请求中包含plantingCount字段）
            if (user.getPlantingCount() != null) {
                existingUser.setPlantingCount(user.getPlantingCount());
                needUpdate = true;
            }

            // 7. 执行更新
            if (needUpdate) {
                boolean success = this.updateById(existingUser);
                if (success) {
                    return Result.success("更新成功");
                } else {
                    return Result.error("更新失败");
                }
            }

            return Result.error("没有提供要更新的字段");
        } catch (Exception e) {
            return Result.error("更新用户失败: " + e.getMessage());
        }
    }

    @Override
    public Result deleteUser(Long id) {
        try {
            User existingUser = this.getById(id);
            if (existingUser == null) {
                return Result.error("用户不存在");
            }

            boolean success = this.removeById(id);
            if (success) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除用户失败: " + e.getMessage());
        }
    }

    @Override
    public Result register(User user) {
        try {
            // 检查用户名是否已存在
            User existingUser = findByUsername(user.getUsername());
            if (existingUser != null) {
                return Result.error("用户名已存在");
            }

            // 密码加密
            String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(encryptedPassword);

            // 设置默认值
            user.setRole("USER");
            user.setMaintenanceCount(0);
            user.setPlantingCount(0);

            boolean success = this.save(user);
            if (success) {
                // 注册成功后隐藏密码
                user.setPassword(null);
                return Result.success("注册成功");
            } else {
                return Result.error("注册失败");
            }
        } catch (Exception e) {
            return Result.error("注册失败: " + e.getMessage());
        }
    }

    @Override
    public Result userlogin(String username, String password) {
        try {
            User user = findByUsername(username);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 验证密码
            String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!user.getPassword().equals(encryptedPassword)) {
                return Result.error("密码错误");
            }
            // 验证角色是否为USER
            if (!"USER".equals(user.getRole())) {
                return Result.error("非用户账号，无法登录");
            }
            // 生成token（使用JWT）
            String token = jwtHelper.createToken(Long.valueOf(user.getId()));

            // 构建响应数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            return Result.success("登录成功", data);
        } catch (Exception e) {
            return Result.error("登录失败: " + e.getMessage());
        }
    }
    @Override
    public Result adminlogin(String username, String password) {
        try {
            User user = findByUsername(username);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 验证密码
            String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!user.getPassword().equals(encryptedPassword)) {
                return Result.error("密码错误");
            }
            // 验证角色是否为ADMIN
            if (!"ADMIN".equals(user.getRole())) {
                return Result.error("非管理员账号，无法登录");
            }

            // 生成token（使用JWT）
            String token = jwtHelper.createToken(Long.valueOf(user.getId()));

            // 构建响应数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            return Result.success("登录成功", data);
        } catch (Exception e) {
            return Result.error("登录失败: " + e.getMessage());
        }
    }

    @Override
    public Result logout() {
        try {
            // 在实际项目中，这里应该处理token失效等逻辑
            return Result.success("登出成功");
        } catch (Exception e) {
            return Result.error("登出失败: " + e.getMessage());
        }
    }

}




