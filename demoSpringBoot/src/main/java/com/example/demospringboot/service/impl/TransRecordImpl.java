package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Trans;
import com.example.demospringboot.domain.TransRecord;
import com.example.demospringboot.mapper.TransRecordMapper;
import com.example.demospringboot.service.TransRecordService;
import com.example.demospringboot.service.TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransRecordImpl implements TransRecordService {
    @Autowired
    private TransRecordMapper transRecordMapper;

    @Override
    public int setAnswerRecord(int userId, int transId, String u_trans) {
        QueryWrapper<TransRecord> queryWrapper=new QueryWrapper<>();
        TransRecord transRecord=new TransRecord();
        Map<String,String> map=new HashMap<>();
        map.put("u_trans",u_trans);

        transRecord.setU_trans(map);
        transRecord.setTransId(transId);
        transRecord.setUserId(userId);
        transRecord.setUpdatetime(LocalDateTime.now());
        Long count=findAnswerRecord(userId,transId);
        if(count==1)//存在
        {queryWrapper.eq("userId",userId).eq("transId",transId);
            transRecordMapper.update(transRecord,queryWrapper);
        } else if (0==count) {
            transRecordMapper.insert(transRecord);
        } else if (count>1) {//有两条以上相同数据，从控制台报错，然后不做任何动作
            System.out.println("error: have same data userId:"+userId+"  transId: "+transId+" !!!!");
        }
        return 0;
    }

    @Override
    public Long findAnswerRecord(int userId, int transId) {
        Long i;
        QueryWrapper<TransRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",userId).eq("transId",transId);
        i=transRecordMapper.selectCount(queryWrapper);

        return i;
    }

    @Override
    public String returnAnswerRecord(int userId, int trans_recordId) {
        return null;
    }
}
