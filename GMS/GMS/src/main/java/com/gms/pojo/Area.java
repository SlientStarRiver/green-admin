package com.gms.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName area
 */
@TableName(value ="area")
@Data
public class Area {
    @TableId
    private String areaId;

    private String areaName;

    private BigDecimal areaSize;

    private String locationDescription;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
}