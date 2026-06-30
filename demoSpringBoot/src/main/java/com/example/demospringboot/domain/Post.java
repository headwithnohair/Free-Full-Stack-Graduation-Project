package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@TableName(value = "post",autoResultMap = true)
public class Post {

    @TableId(value = "postid", type = IdType.AUTO)
    private Integer postid;
    private Integer userid;
    private String  title;
    private String  content;
    @TableField(typeHandler = JacksonTypeHandler.class,value = "picture")
    private Map<String, List<String>> picture;
    private Integer likes;
    private Integer hate;
    private LocalDateTime uploadtime;
    private LocalDateTime changetime;
}
