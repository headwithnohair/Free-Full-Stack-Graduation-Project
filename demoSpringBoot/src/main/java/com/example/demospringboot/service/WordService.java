package com.example.demospringboot.service;

import com.example.demospringboot.domain.Word;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface WordService {
        List<Word> getWordDetail (List<Integer> dayBookList);//注意null值

        List<Word> obscureSreachWord(String word,int limit);//模糊查询


}
