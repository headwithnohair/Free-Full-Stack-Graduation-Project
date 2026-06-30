package com.example.demospringboot.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



@Data
@TableName ( "books")
public class Book {
    @TableId
    private Integer bookid;


    private String bookname;


    private  Integer userchoose;


    private  Integer wordCount;

}
