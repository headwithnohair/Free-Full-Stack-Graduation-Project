package com.example.demospringboot.controller;


import com.alibaba.fastjson2.JSON;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.service.DayBookService;
import com.example.demospringboot.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("DBC")
@RestController
public class DayBookController {

    @Autowired
    DayBookService dayBookService;
    @Autowired
    WordService wordService;


    @GetMapping("wrongList")
    public String wrongList(int userId){
        List<Integer> tt=new ArrayList<>();
        List<Word> temp;
        tt.add(dayBookService.nextWrongword(userId));
        temp=wordService.getWordDetail(tt);

        return JSON.toJSONString(temp.get(0));
    }


    @GetMapping("/statius")
    public String statius(int userId){
        String tt=dayBookService.endWrongList(userId);

        return  tt;
    }
    @GetMapping("test")
    public  String test( int userId){
        String ttt = String.valueOf(dayBookService.nextWrongword(userId));
        return ttt ;
    }
}
