package com.example.demospringboot.service;

import com.example.demospringboot.domain.Word;

import java.util.List;

public interface WordBookService {

    List<Word> getReciteWordList_no(int bookId,int number,int userId);//不随机

    List<Word> getReviewWordList_no(int bookId,int number,int userId,List<Word> userList);

    List<Word> getAllWordList(int bookId,int limit,List<Word> wordList);
}
