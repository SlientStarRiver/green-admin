package com.gms.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * @TableName maintenance_record
 */
@TableName(value ="maintenance_record")
@Data
public class MaintenanceRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String areaId;

    private String speciesId;

    private Date maintenanceTime;

    private Integer plantCount;

    private String maintainer;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
}