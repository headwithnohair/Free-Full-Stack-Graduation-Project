package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



@Data
@TableName( "words")
public class Word {

@TableId
    private Integer word_id;

    private String word_name;


    private String word_say;


    private String word_tran;


    private String word_ex;


    private String word_sound;
}
