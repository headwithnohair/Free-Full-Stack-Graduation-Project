package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "trans",autoResultMap = true)
public class Trans {

    @TableId
    private Integer trans_id;
    @TableField(typeHandler = JacksonTypeHandler.class,value = "trans")
    private Map<String,String> trans;
    @TableField(typeHandler = JacksonTypeHandler.class,value = "content")
    private Map<String,String>  content;
    private LocalDateTime uploadtime;
    private int bookId;

}
