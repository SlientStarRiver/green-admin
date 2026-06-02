package com.gms.mapper;

import com.gms.pojo.Area;
import com.gms.pojo.PlantingRecord;
import com.gms.pojo.PlantSpecies;
import com.gms.pojo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 多模块集成测试
 * 测试模块间的关联关系和数据一致性
 */
public class RepositoryIntegrationTest extends BaseMapperTest {

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private PlantingRecordMapper plantingRecordMapper;

    @Autowired
    private PlantSpeciesMapper plantSpeciesMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("测试完整的种植记录流程")
    @Transactional
    public void testCompletePlantingWorkflow() {
        // 1. 创建测试用户
        User user = new User();
        user.setUsername("integration_user");
        user.setPassword("password123");
        user.setPhone("13812345678");
        user.setRole("USER");
        userMapper.insert(user);
        assertNotNull(user.getId());

        // 2. 创建测试区块
        Area area = new Area();
        area.setAreaId("INTEGRATION_AREA");
        area.setAreaName("集成测试区块");
        area.setAreaSize(new BigDecimal("500.00"));
        area.setLocationDescription("集成测试位置");
        areaMapper.insert(area);
        assertNotNull(areaMapper.selectById("INTEGRATION_AREA"));

        // 3. 创建测试植物品种
        PlantSpecies species = new PlantSpecies();
        species.setSpeciesId("INTEGRATION_SPECIES");
        species.setSpeciesName("集成测试植物");
        plantSpeciesMapper.insert(species);
        assertNotNull(plantSpeciesMapper.selectById("INTEGRATION_SPECIES"));

        // 4. 创建种植记录（关联用户、区块、品种）
        PlantingRecord record = new PlantingRecord();
        record.setAreaId("INTEGRATION_AREA");
        record.setSpeciesId("INTEGRATION_SPECIES");
        record.setPlantingTime(new Date());
        record.setPlantCount(150);
        record.setResponsiblePerson("integration_user");
        record.setDescription("集成测试种植记录");

        plantingRecordMapper.insert(record);
        assertNotNull(record.getId());

        // 5. 验证关联查询
        PlantingRecord insertedRecord = plantingRecordMapper.selectById(record.getId());
        assertNotNull(insertedRecord);
        assertEquals("INTEGRATION_AREA", insertedRecord.getAreaId());
        assertEquals("INTEGRATION_SPECIES", insertedRecord.getSpeciesId());
        assertEquals("integration_user", insertedRecord.getResponsiblePerson());
    }

    @Test
    @DisplayName("测试数据一致性验证")
    public void testDataConsistency() {
        // 创建用户
        User user = new User();
        user.setUsername("consistency_user");
        user.setPassword("password");
        user.setPhone("13912345678");
        user.setRole("ADMIN");
        userMapper.insert(user);

        // 创建区块
        Area area = new Area();
        area.setAreaId("CONSISTENCY_AREA");
        area.setAreaName("一致性测试区块");
        area.setAreaSize(new BigDecimal("300.00"));
        areaMapper.insert(area);

        // 验证用户和区块可以独立查询
        User foundUser = userMapper.findByUsername("consistency_user");
        assertNotNull(foundUser);
        assertEquals("ADMIN", foundUser.getRole());

        Area foundArea = areaMapper.selectById("CONSISTENCY_AREA");
        assertNotNull(foundArea);
        assertEquals("一致性测试区块", foundArea.getAreaName());

        // 验证用户计数
        Long userCount = userMapper.selectCount(null);
        assertTrue(userCount > 0);

        // 验证区块计数
        Long areaCount = areaMapper.selectCount(null);
        assertTrue(areaCount > 0);
    }
}