package com.example.demospringboot.service;

import com.example.demospringboot.domain.UserPlan;
import com.example.demospringboot.domain.Word;

import java.util.List;

public interface UserPlanService {
    List<Word> getUserReciteList(Integer userId);
    List<Word> getUserReviwList(Integer userId);

    UserPlan getUserPlan(Integer userId);

    String changeUserLoginTime(Integer userId);

    int updateRe(int userId,int judge);

    int updateUserPreferences(UserPlan userPlan);
    int resetUserNow(int userId);

    String setdefaultPlan(int wxopenId);

    int changeUserBook(int userId,int bookId);

    int resetUserPlanTime(int userId);

}
