package com.gms.mapper;

import com.gms.pojo.Area;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 区块管理Mapper单元测试
 */
public class AreaMapperTest extends BaseMapperTest {

    @Autowired
    private AreaMapper areaMapper;

    private Area testArea;

    @BeforeEach
    public void setUp() {
        // 准备测试数据
        testArea = new Area();
        testArea.setAreaId(generateTestId("AREA"));
        testArea.setAreaName("测试区块");
        testArea.setAreaSize(new BigDecimal("150.75"));
        testArea.setLocationDescription("测试位置");
    }

    @Test
    @DisplayName("测试插入区块")
    public void testInsertArea() {
        int result = areaMapper.insert(testArea);
        assertEquals(1, result);

        Area inserted = areaMapper.selectById(testArea.getAreaId());
        assertNotNull(inserted);
        assertEquals("测试区块", inserted.getAreaName());
    }

    @Test
    @DisplayName("测试更新区块")
    public void testUpdateArea() {
        areaMapper.insert(testArea);

        testArea.setAreaName("更新后的区块");
        int result = areaMapper.updateById(testArea);

        assertEquals(1, result);
        Area updated = areaMapper.selectById(testArea.getAreaId());
        assertEquals("更新后的区块", updated.getAreaName());
    }

    @Test
    @DisplayName("测试删除区块")
    public void testDeleteArea() {
        areaMapper.insert(testArea);

        int result = areaMapper.deleteById(testArea.getAreaId());

        assertEquals(1, result);
        assertNull(areaMapper.selectById(testArea.getAreaId()));
    }

    @Test
    @DisplayName("测试按名称查询区块")
    public void testSelectByAreaName() {
        areaMapper.insert(testArea);

        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.like("area_name", "测试");

        List<Area> areas = areaMapper.selectList(wrapper);
        assertFalse(areas.isEmpty());
        assertEquals("测试区块", areas.get(0).getAreaName());
    }

}