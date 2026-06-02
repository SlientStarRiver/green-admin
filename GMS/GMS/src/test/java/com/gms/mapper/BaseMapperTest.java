package com.gms.mapper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础Mapper测试类
 * 提供通用配置和工具方法
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public abstract class BaseMapperTest {

    /**
     * 生成测试ID
     */
    protected String generateTestId(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    /**
     * 验证实体字段
     */
    protected void assertEntityFields(Object entity, String fieldName, Object expectedValue) {
        try {
            java.lang.reflect.Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object actualValue = field.get(entity);
            org.junit.jupiter.api.Assertions.assertEquals(expectedValue, actualValue);
        } catch (Exception e) {
            throw new RuntimeException("验证字段失败: " + fieldName, e);
        }
    }
}