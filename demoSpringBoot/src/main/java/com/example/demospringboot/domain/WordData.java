package com.example.demospringboot.domain;

import lombok.Data;

import java.util.List;
@Data
public class WordData {
    //交给前端数据的结构体
    private List<Word> noRecite;
    private List<Word> alRecite;
    private List<Word> noReview;
    private List<Word> alReview;
    private UserPlan userPlan;//拿id
}
