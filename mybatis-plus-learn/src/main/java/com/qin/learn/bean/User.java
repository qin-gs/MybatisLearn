package com.qin.learn.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private int age;
    private String email;

    // 自动更新
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 乐观锁
    @Version
    private int version;
    // 逻辑删除
    @TableLogic
    private int deleted;
}
