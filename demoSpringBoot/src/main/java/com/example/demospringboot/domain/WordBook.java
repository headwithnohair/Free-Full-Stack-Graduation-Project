package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



@Data
@TableName( "word_book")
public class WordBook {
    @TableId
    private  Integer word_id;


    private  Integer bookid;

}
