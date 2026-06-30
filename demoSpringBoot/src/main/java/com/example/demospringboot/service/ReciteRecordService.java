package com.example.demospringboot.service;

import com.example.demospringboot.domain.Word;

import java.time.LocalDateTime;
import java.util.List;

public interface ReciteRecordService {
    List<Word> getReviewWordList_al(int bookId, int number, int userId);//复习， 获取当日背诵列表，找出reciterecord中谁在表内，addlist
    List<Word> getReciteWordList_al(int bookId,int number,int userId);//提供给“查看此表”功能，主要返回单词内容
    Long userHoldTime(int userId, int bookId, LocalDateTime plan_start);

    Long countReciteWord(int userId, int bookId);

    Integer updateWordRecord(int userId,int wordId,int bookId,int rf_judge);

    int getOneReviewWord(int userId,int bookId);

    LocalDateTime getOneReviewLastDate(int userId,int wordId);

}
