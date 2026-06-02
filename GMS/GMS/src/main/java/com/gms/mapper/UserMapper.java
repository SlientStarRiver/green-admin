package com.gms.mapper;

import com.gms.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author HP
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-11-20 13:10:10
* @Entity com.gms.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
}




