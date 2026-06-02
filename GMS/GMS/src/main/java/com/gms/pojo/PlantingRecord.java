package com.gms.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * @TableName planting_record
 */
@TableName(value ="planting_record")
@Data
public class PlantingRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String areaId;

    private String speciesId;

    private Date plantingTime;

    private Integer plantCount;

    private String responsiblePerson;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
}