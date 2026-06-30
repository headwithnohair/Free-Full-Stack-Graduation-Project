package com.example.demospringboot.controller;

import com.alibaba.fastjson2.JSON;
import com.example.demospringboot.domain.ReciteRecord;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.service.DayBookService;
import com.example.demospringboot.service.ReciteRecordService;
import com.example.demospringboot.service.UserPlanService;
import com.example.demospringboot.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("RRC")
@RestController
public class ReciteRecordController {
    @Autowired
    private ReciteRecordService reciteRecordService;
    @Autowired
    private DayBookService dayBookService;
    @Autowired
    private UserPlanService userPlanService;
    @Autowired
    private WordService wordService;

    @GetMapping("/record")
    public String record(int userId, int wordId, int bookId, int rf_judge,int judge){//之三复习的   ,错词大于三次不添加 设计10个一组背诵复习词
        //

        reciteRecordService.updateWordRecord(userId,wordId,bookId,rf_judge);
        dayBookService.listUpdater(wordId,rf_judge,judge,userId);
        userPlanService.updateRe(userId,judge);
        return  null;
    }

    @GetMapping("/oneReviewWord")
    public  String oneReviewWord(int userId,int bookId){
        List<Integer> tt=new ArrayList<>();
        List<Word> word;
        tt.add(reciteRecordService.getOneReviewWord(userId,bookId));
        word=wordService.getWordDetail(tt);
        //+JSON.toJSONString(reciteRecordService.getOneReviewLastDate(userId, tt.get(0)))
        return JSON.toJSONString(word.get(0));
    }

    @GetMapping("/listenRecoord")
    public String listenRecoord(int userId,int bookId,int wordId,int rf_judge){
        reciteRecordService.updateWordRecord(userId,wordId,bookId,rf_judge);
        return null;
    }

}
