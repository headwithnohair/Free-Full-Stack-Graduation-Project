package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Trans;
import com.example.demospringboot.mapper.TransMapper;
import com.example.demospringboot.mapper.TransRecordMapper;
import com.example.demospringboot.service.TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TransImpl implements TransService {
    @Autowired
    private TransMapper transMapper;
    @Autowired
    private TransRecordMapper transRecordMapper;
    @Override
    public Trans getOneTran(int bookId) {//

        return null;
    }

    @Override
    public Trans getOneTran(int bookId, int userId) {
        QueryWrapper<Trans> queryWrapper=new QueryWrapper<>();
        Trans trans;
        queryWrapper
                .eq("bookId",bookId).last("limit "+ 1+"")
                .notInSql("trans_id","select transId from trans_record where userId="+userId+"");
        trans =transMapper.selectOne(queryWrapper);//小心null
        //System.out.println(trans);
        return trans;
    }


}
