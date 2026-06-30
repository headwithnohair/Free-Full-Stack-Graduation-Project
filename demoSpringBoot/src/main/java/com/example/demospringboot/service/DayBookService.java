package com.example.demospringboot.service;

import com.example.demospringboot.domain.DayBook;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.domain.WordData;

public interface DayBookService {

    Boolean checkUserData(Integer userId);

    String insertUserData(WordData wordData);//需要确认值非空


    DayBook getDayBook(DayBook dayBook);

    Integer listUpdater(int wordId,int  rf_judge,int judge,int userId);

    String endWrongList(int userId );

    int   nextWrongword(int userId);

}
