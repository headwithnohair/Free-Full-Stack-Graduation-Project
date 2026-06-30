package com.example.demospringboot.controller;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Book;
import com.example.demospringboot.domain.Trans;
import com.example.demospringboot.domain.TransRecord;
import com.example.demospringboot.mapper.TransMapper;
import com.example.demospringboot.mapper.TransRecordMapper;
import com.example.demospringboot.service.TransRecordService;
import com.example.demospringboot.service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("Trans")
@RestController

public class TransController {
@Autowired
    private TransService transService;
@Autowired
    private TransRecordService transRecordService;


@GetMapping("/normalonetran")
public String  normalonetran(int userId,int bookId){
    Trans trans;
    trans=transService.getOneTran(bookId,userId);
    if (trans!=null)
    {
        Map<String,String> tranMap=new HashMap<>();
        tranMap.put("trans",trans.getTrans().get("trans"));
        tranMap.put("content",trans.getTrans().get("content"));
        tranMap.put("transId",trans.getTrans_id().toString());
        return JSON.toJSONString(tranMap);
    }else
    return "null";
}

@GetMapping("/setAnswerRecord")
public String setAnswerRecord(int userId, int transId, String u_trans){
    transRecordService.setAnswerRecord( userId,  transId,  u_trans);
    return "done";
}

@GetMapping("/test")
public int test(){

    return 0;
}

}
