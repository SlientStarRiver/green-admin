package com.gms.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * @TableName plant_species
 */
@TableName(value ="plant_species")
@Data
public class PlantSpecies {
    @TableId
    private String speciesId;

    private String speciesName;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
}