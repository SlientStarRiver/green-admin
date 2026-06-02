package com.gms.mapper;

import com.gms.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户管理Mapper单元测试
 */
public class UserMapperTest extends BaseMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // 准备测试数据
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("encodedpassword");
        testUser.setPhone("13800138000");
        testUser.setRole("USER");
    }

    @Test
    @DisplayName("测试插入用户")
    public void testInsertUser() {
        int result = userMapper.insert(testUser);
        assertEquals(1, result);

        assertNotNull(testUser.getId(), "ID应该被自动生成");

        User inserted = userMapper.selectById(testUser.getId());
        assertNotNull(inserted);
        assertEquals("testuser", inserted.getUsername());
    }

    @Test
    @DisplayName("测试按用户名查询用户")
    public void testFindByUsername() {
        userMapper.insert(testUser);

        User found = userMapper.findByUsername("testuser");
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
        assertEquals("13800138000", found.getPhone());
    }

    @Test
    @DisplayName("测试按角色查询用户")
    public void testSelectByRole() {
        userMapper.insert(testUser);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role", "USER");

        List<User> users = userMapper.selectList(wrapper);
        assertFalse(users.isEmpty());
        assertEquals("USER", users.get(0).getRole());
    }

    @Test
    @DisplayName("测试按手机号查询用户")
    public void testSelectByPhone() {
        userMapper.insert(testUser);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", "13800138000");

        List<User> users = userMapper.selectList(wrapper);
        assertFalse(users.isEmpty());
        assertEquals("13800138000", users.get(0).getPhone());
    }

    @Test
    @DisplayName("测试更新用户信息")
    public void testUpdateUser() {
        userMapper.insert(testUser);

        testUser.setUsername("updateduser");
        testUser.setPhone("13900139000");

        int result = userMapper.updateById(testUser);

        assertEquals(1, result);
        User updated = userMapper.selectById(testUser.getId());
        assertEquals("updateduser", updated.getUsername());
        assertEquals("13900139000", updated.getPhone());
    }

    @Test
    @DisplayName("测试删除用户")
    public void testDeleteUser() {
        userMapper.insert(testUser);

        int result = userMapper.deleteById(testUser.getId());

        assertEquals(1, result);
        assertNull(userMapper.selectById(testUser.getId()));
    }

    @Test
    @DisplayName("测试统计用户数量")
    public void testCountUsers() {
        // 准备多条测试数据
        for (int i = 1; i <= 4; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("pass" + i);
            user.setPhone("1380000" + String.format("%04d", i));
            user.setRole(i % 2 == 0 ? "ADMIN" : "USER");
            userMapper.insert(user);
        }

        Long totalCount = userMapper.selectCount(null);
        assertTrue(totalCount >= 4);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role", "ADMIN");

        Long adminCount = userMapper.selectCount(wrapper);
        assertTrue(adminCount >= 2);
    }
}