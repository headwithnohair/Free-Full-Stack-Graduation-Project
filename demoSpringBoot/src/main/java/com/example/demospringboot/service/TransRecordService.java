package com.example.demospringboot.service;

public interface TransRecordService {

    int setAnswerRecord(int userId,int transId,String u_trans);
    Long findAnswerRecord(int userId,int transId);//找有无以前记录

    String returnAnswerRecord(int userId,int trans_recordId);
}
