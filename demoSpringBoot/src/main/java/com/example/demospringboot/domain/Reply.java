package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "reply",autoResultMap = true)
public class Reply {

    @TableId(value = "replyid", type = IdType.AUTO)
    private  Integer replyid;
    private  Integer userid;
    private  Integer targetid;
    private  String reply;
    private     Integer likes;
    private     Integer hate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadtime;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private    LocalDateTime updatetime;
}
