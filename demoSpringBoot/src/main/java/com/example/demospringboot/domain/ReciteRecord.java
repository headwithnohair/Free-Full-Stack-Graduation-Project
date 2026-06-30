package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName ("recite_record")
public class ReciteRecord {

@TableId(value = "record_id", type = IdType.AUTO)
    private Integer record_id;


    private Integer userid;


    private Integer word_id;


    private Integer bookid;

    private Integer wrongtimes;


    private Integer showtimes;


    private LocalDateTime firstdate;


    private LocalDateTime lastdate;


}
