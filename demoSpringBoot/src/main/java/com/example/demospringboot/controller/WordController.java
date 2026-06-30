package com.example.demospringboot.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.service.UserPlanService;
import com.example.demospringboot.service.WordBookService;
import com.example.demospringboot.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("Word")
@RestController
public class WordController {
    @Autowired
    private WordService wordService;
    @Autowired
    private UserPlanService userPlanService;
    @Autowired
    private WordBookService wordBookService;

    @GetMapping("getAllWordList")
    public String getAllWordList(int bookId,int limit){
        List<Word> list=new ArrayList<>();
        wordBookService.getAllWordList(bookId,limit,list);

        return JSONObject.toJSONString(list);
    }
    @GetMapping("/obscureSreachWord")
    public String obscureSreachWord(String word,int limit){
        List<Word> nee;
        nee=wordService.obscureSreachWord(word,limit);
        return JSONObject.toJSONString(nee);
    }
}
