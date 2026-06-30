package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.User;
import com.example.demospringboot.domain.UserPlan;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.domain.WordData;
import com.example.demospringboot.mapper.UserMapper;
import com.example.demospringboot.mapper.UserPlanMapper;
import com.example.demospringboot.service.UserPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserPlanImpl implements UserPlanService {
    @Autowired
    private UserPlanMapper userPlanMapper;


    @Override
    public List<Word> getUserReciteList(Integer userId) {
        List<Word> WordList =new ArrayList<>();//userid找plan找书》获取wordid>生成wordlist;
        UserPlan temp =new UserPlan();
        UserPlan plan ;
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        temp.setUserid(userId);
        plan=userPlanMapper.selectOne(queryWrapper);
        if(plan==null)
        {
            System.out.println("userPlanMapper.selectOne(temp)==NULL;error");
            return null;
        }

        return WordList;
    }

    @Override
    public List<Word> getUserReviwList(Integer userId) {
        List<Word> WordList =new ArrayList<>();
        UserPlan temp =new UserPlan();
        UserPlan plan ;
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        temp.setUserid(userId);
        plan=userPlanMapper.selectOne(queryWrapper);
        if(plan==null)
        {
            System.out.println("userPlanMapper.selectOne(temp)==NULL;error");
            return null;
        }
        return WordList;
    }

    @Override
    public UserPlan getUserPlan(Integer userId) {
        UserPlan temp;
        UserPlan plan ;
        temp =new UserPlan();
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        temp.setUserid(userId);
        plan=userPlanMapper.selectOne(queryWrapper);
        if(plan==null)
        {
            System.out.println("userPlanMapper.selectOne(temp)==NULL;error");
            return null;
        }
        return plan;
    }

    @Override
    public String changeUserLoginTime(Integer userId) {
        UserPlan temp =new UserPlan();
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userId);
        temp=userPlanMapper.selectOne(queryWrapper);
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0, 0);
        temp.setLast_in_app(dateTime);
        userPlanMapper.update(temp,queryWrapper);//留意并发?
        return  null;
    }

    @Override
    public int updateRe(int userId,int judge) {
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userId);
        UserPlan userPlan;
        userPlan=userPlanMapper.selectOne(queryWrapper);
        if (judge==1){
            userPlan.setNow_cite(userPlan.getNow_cite()+1);
        } else if (judge==2) {
            userPlan.setNow_re(userPlan.getNow_re()+1);
        }

        userPlanMapper.update(userPlan,queryWrapper);
        return 0;
    }

    @Override
    public int updateUserPreferences(UserPlan userPlan) {
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userPlan.getUserid());
        userPlanMapper.update(userPlan,queryWrapper);
        return 0;
    }

    @Override
    public int resetUserNow(int userId) {
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userId);
        UserPlan userPlan;
        userPlan=userPlanMapper.selectOne(queryWrapper);//0
        String t1=userPlan.getLast_in_app().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String t2=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if(t2.compareTo(t1)!=0)
        {System.out.println("????");
            userPlan.setNow_re(0);
        userPlan.setNow_cite(0);}
        userPlanMapper.update(userPlan,queryWrapper);
        return 0;
    }

    @Override
    public String setdefaultPlan(int userId) {
        Map<String,String> map=new HashMap<>();
        UserPlan userPlan=new UserPlan();
        QueryWrapper<UserPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userId);
        Long count=userPlanMapper.selectCount(queryWrapper);

        if(count==1)
        {
            return "we have one ";
        }else if (count==0)
        { LocalDateTime sign=LocalDateTime.now();
            map.put("lantype","uk");
            map.put("wordSize","normal");
            map.put("autoSay","true");
            userPlan.setUserscore("加油！");
            userPlan.setUserid(userId);
            userPlan.setPlan_cite(10);
            userPlan.setPlan_re(10);
            userPlan.setBookid(1);
            userPlan.setNow_cite(0);
            userPlan.setNow_re(0);
            userPlan.setLast_in_app(sign);
            userPlan.setDeadline(sign.plusMonths(3));
            userPlan.setPlan_start(sign.minusMonths(3));
            userPlan.setPreferences(map);
            userPlanMapper.insert(userPlan);
            System.out.println("新增一个 userplan userID:  "+userId);
        }
        return null;
    }

    @Override
    public int changeUserBook(int userId, int bookId) {
        QueryWrapper<UserPlan>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        UserPlan userPlan=userPlanMapper.selectOne(queryWrapper);
        userPlan.setBookid(bookId);
        userPlanMapper.update(userPlan,queryWrapper);
        return 0;
    }

    @Override
    public int resetUserPlanTime(int userId) {
        UserPlan userPlan;
        QueryWrapper<UserPlan> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        userPlan= userPlanMapper.selectOne(queryWrapper);
        userPlan.setPlan_start(LocalDateTime.now());
        userPlanMapper.update(userPlan,queryWrapper);
        return 0;
    }


}
