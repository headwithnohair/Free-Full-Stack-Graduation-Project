package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "trans_Record",autoResultMap = true)
public class TransRecord {
    @TableId
    private Integer trans_recordId;
    private int transId;
    private int userId;
    @TableField(typeHandler = JacksonTypeHandler.class,value = "u_trans")
    private Map<String,String> u_trans;
    private LocalDateTime updatetime;

}

