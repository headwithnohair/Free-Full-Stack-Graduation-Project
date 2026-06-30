package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;

@Data
@TableName("user_table")
public class User  {

    /*user_table(userid,username,usericon,nickname
              ,postcount,signtime,likes,hate,fans);*/
    @TableId(value = "userid", type = IdType.AUTO)
    private  Integer userid;
    private  String username;
    private  String usericon;
    private    String nickname;
    private  Integer postcount;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signtime;
    private Integer likes;
    private Integer hate;
    private Integer fans;
    private String password;
    private  String wx_openid;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
