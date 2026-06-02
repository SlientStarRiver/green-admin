package com.gms.mapper;

import com.gms.pojo.PlantSpecies;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 植物品种Mapper单元测试
 */
public class PlantSpeciesMapperTest extends BaseMapperTest {

    @Autowired
    private PlantSpeciesMapper plantSpeciesMapper;

    private PlantSpecies testSpecies;

    @BeforeEach
    public void setUp() {
        // 准备测试数据
        testSpecies = new PlantSpecies();
        testSpecies.setSpeciesId(generateTestId("SPECIES"));
        testSpecies.setSpeciesName("测试植物");
    }

    @Test
    @DisplayName("测试插入植物品种")
    public void testInsertSpecies() {
        int result = plantSpeciesMapper.insert(testSpecies);
        assertEquals(1, result);

        PlantSpecies inserted = plantSpeciesMapper.selectById(testSpecies.getSpeciesId());
        assertNotNull(inserted);
        assertEquals("测试植物", inserted.getSpeciesName());
    }

    @Test
    @DisplayName("测试按名称查询植物品种")
    public void testSelectBySpeciesName() {
        plantSpeciesMapper.insert(testSpecies);

        QueryWrapper<PlantSpecies> wrapper = new QueryWrapper<>();
        wrapper.like("species_name", "测试");

        List<PlantSpecies> speciesList = plantSpeciesMapper.selectList(wrapper);
        assertFalse(speciesList.isEmpty());
        assertEquals("测试植物", speciesList.get(0).getSpeciesName());
    }

    @Test
    @DisplayName("测试查询所有植物品种")
    public void testSelectAllSpecies() {
        // 准备多条测试数据
        for (int i = 1; i <= 3; i++) {
            PlantSpecies species = new PlantSpecies();
            species.setSpeciesId("SPECIES" + i);
            species.setSpeciesName("植物" + i);
            plantSpeciesMapper.insert(species);
        }

        List<PlantSpecies> allSpecies = plantSpeciesMapper.selectList(null);
        assertTrue(allSpecies.size() >= 3);
    }

    @Test
    @DisplayName("测试删除植物品种")
    public void testDeleteSpecies() {
        plantSpeciesMapper.insert(testSpecies);

        int result = plantSpeciesMapper.deleteById(testSpecies.getSpeciesId());

        assertEquals(1, result);
        assertNull(plantSpeciesMapper.selectById(testSpecies.getSpeciesId()));
    }

    @Test
    @DisplayName("测试更新植物品种信息")
    public void testUpdateSpecies() {
        plantSpeciesMapper.insert(testSpecies);

        testSpecies.setSpeciesName("更新后的植物");

        int result = plantSpeciesMapper.updateById(testSpecies);

        assertEquals(1, result);
        PlantSpecies updated = plantSpeciesMapper.selectById(testSpecies.getSpeciesId());
        assertEquals("更新后的植物", updated.getSpeciesName());
    }
}