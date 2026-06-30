package com.example.demospringboot.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.*;
import com.example.demospringboot.mapper.BookMapper;
import com.example.demospringboot.mapper.DayBookMapper;
import com.example.demospringboot.mapper.ReciteRecordMapper;
import com.example.demospringboot.mapper.UserPlanMapper;
import com.example.demospringboot.service.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("userplan")
@RestController
public class UserPlanController {
    @Autowired
    private WordService wordService;
    @Autowired
    private UserPlanService userPlanService;
    @Autowired
    private WordBookService wordBookService;

    @Autowired
    private DayBookService dayBookService;
    @Autowired
    private BookService bookService;

    @Autowired
    private ReciteRecordService reciteRecordService;

    @GetMapping("/getDayWord")
    public String getDayWord(int userId)
    {WordData gave =new WordData();


        if(dayBookService.checkUserData(userId)){  //从daybook检测用户当日有无二次进入小程序
            System.out.println("正在为用户id: "+userId+" 获取当日数据");//修改userplan中的背诵
            List<Word> noRecite;
            UserPlan userPlan;
            DayBook dayBook=new DayBook();
            dayBook.setUserId(userId);
            userPlan=userPlanService.getUserPlan(userId);
//            userPlan.setPlan_cite(userPlan.getPlan_cite()-userPlan.getNow_cite());
//            userPlan.setPlan_re(userPlan.getPlan_re()-userPlan.getNow_re());
            gave.setUserPlan(userPlan);
            dayBook=dayBookService.getDayBook(dayBook);
            noRecite=wordService.getWordDetail(dayBook.getNoRecite().get("noRecite"));
            gave.setNoRecite(noRecite);
            gave.setNoReview(wordService.getWordDetail(dayBook.getNoRecite().get("noReview")));

            System.out.println("用户二次进入");


            return  ""+JSON.toJSONString(gave);
        }else{
        List<Word> temp;

        userPlanService.resetUserNow(userId);
        gave.setUserPlan(userPlanService.getUserPlan(userId));
        gave.setNoRecite(wordBookService.getReciteWordList_no(gave.getUserPlan().getBookid(),gave.getUserPlan().getPlan_cite(), gave.getUserPlan().getUserid()));

        temp = gave.getNoRecite().stream().collect(Collectors.toList());//浅拷贝，深拷贝问题
        gave.setNoReview( wordBookService.getReviewWordList_no(gave.getUserPlan().getBookid() ,gave.getUserPlan().getPlan_re() , gave.getUserPlan().getUserid(),temp));

        userPlanService.changeUserLoginTime(gave.getUserPlan().getUserid());//似乎当日第二次更新时间（在手动修改数据库时间后）时
            // ，程序无法修改数据，

        dayBookService.insertUserData(gave);
            System.out.println("用户id: "+userId+" 今日第一次进入！ ");
        return ""+JSON.toJSONString(gave);
        }


    }

    @GetMapping("/getUserBaseData")
    public  String getUserBaseData(Integer userId){
        Map<String, String> post= new HashMap<>();
        UserPlan userPlan=userPlanService.getUserPlan(userId);
        LocalDateTime now = LocalDateTime.now();
        Long day;
        System.out.println("SSSSSSS");
        userPlanService.resetUserNow(userId);

        Long tt=reciteRecordService.userHoldTime(userId,userPlan.getBookid(),userPlan.getPlan_start());//已坚持天数
        post.put("holdDay",tt.toString());
        post.put("bookName",bookService.findBookName(userPlan.getBookid()));
        post.put("deadline_name",userPlan.getUserscore());


        day=now.until(userPlan.getDeadline(), ChronoUnit.DAYS);
        post.put("deadline_day",day.toString());
        Long alReciteCount =reciteRecordService.countReciteWord(userId,userPlan.getBookid());
        Integer notyet=bookService.getBookWordCount(userPlan.getBookid());
        Double precent=alReciteCount*1.0/notyet*100.0;
        DecimalFormat fm=new DecimalFormat("##.00");
        post.put("already_get",alReciteCount.toString());
        post.put("notyet_get",notyet.toString());//bookcount
        post.put("percent_word",fm.format(precent));

        post.put("study_max",userPlan.getPlan_cite().toString());
        post.put("study_count",userPlan.getNow_cite().toString());

        post.put("res_max",userPlan.getPlan_re().toString());
        post.put("res_count",userPlan.getNow_re().toString());
        post.put("userId",userId.toString());
        post.put("bookId",userPlan.getBookid().toString());
        // java对象 转化为 json字符串
        // user_base_data :  已坚持天数（根据bookid+单词最后一天+唯一结果+count()） ，书名，用户目标（score）,距离目标天数,
        //                   以背单词，bookcount,百分比，背诵计划
        return  ""+JSON.toJSONString(post);
    }

    @GetMapping("/getUserPlan")
    public  String  getUserPlan(int userId)
    {   UserPlan userPlan;
        userPlan=userPlanService.getUserPlan(userId);
        System.out.println(userPlan);
        return  JSON.toJSONString(userPlan);
    }
    @GetMapping("/setUserPlan")
    public String setUserPlan( String userPlan){
        UserPlan userPlan1=JSON.parseObject(userPlan, UserPlan.class);
        System.out.println(userPlan1);
        userPlanService.updateUserPreferences(userPlan1);

        return null;
    }

    @GetMapping("/getBookList")
    public  String getBookList(int number){
        List<Book> list=bookService.getBookList(number);

        return  JSON.toJSONString(list) ;
    }

    @GetMapping("/changeUserBook")
    public  String changeUserBook(int userId,int bookId){
        userPlanService.changeUserBook(userId,bookId);
        userPlanService.resetUserPlanTime(userId);
        return null;
    }
    @GetMapping("/test")
    public String test(){

        return  null;
    }

}
