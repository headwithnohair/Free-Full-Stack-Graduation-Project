package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;


import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName( value = "user_plan" ,autoResultMap = true)

public class UserPlan {
    @TableId(type = IdType.AUTO)
    private Integer planid;
    private Integer userid;
    private Integer bookid;
    private Integer plan_cite;
    private Integer now_cite;
    private Integer plan_re;
    private Integer now_re;
    private LocalDateTime last_in_app;
    private LocalDateTime deadline;
    private String userscore;
    private LocalDateTime plan_start;
    @TableField(typeHandler = JacksonTypeHandler.class,value = "preferences")
    private Map<String,String> preferences;
}
