package com.example.demospringboot.service;

import com.example.demospringboot.domain.Trans;

public interface TransService {

    Trans getOneTran(int bookId);
    Trans getOneTran(int bookId,int userId);//找出用户没回答过的


}
