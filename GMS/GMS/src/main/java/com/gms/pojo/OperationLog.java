package com.gms.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

@TableName("operation_log")
@Data
public class OperationLog {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String username;

    private String action;

    private String module;

    private String detail;

    private String ip;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
