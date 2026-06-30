package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@TableName ( value = "daybook",autoResultMap = true)
public class DayBook {
    //存放数据库的结构体，与worddata相对
    @TableId
    private Integer userId;
    @TableField(typeHandler = JacksonTypeHandler.class,value = "noRecite")
    private Map<String, List<Integer>> noRecite;

    private LocalDateTime startDate;

}
