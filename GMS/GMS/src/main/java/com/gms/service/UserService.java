package com.gms.service;

import com.gms.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gms.utils.Result;

/**
* @author HP
* @description 针对表【user】的数据库操作Service
* @createDate 2025-11-20 13:10:10
*/
public interface UserService extends IService<User> {
    User findByUsername(String username);
    Result getUserPage(Integer page, Integer size);
    Result getUserById(Long id);
    Result updateUser(Long id, User user);
    Result deleteUser(Long id);
    Result register(User user);
    Result userlogin(String username, String password);
    Result adminlogin(String username, String password);
    Result logout();
    Result changePassword(Long userId, String oldPassword, String newPassword);
    Result getUserInfo(Long userId);
}
