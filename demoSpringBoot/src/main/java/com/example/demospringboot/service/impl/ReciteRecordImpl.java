package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.ReciteRecord;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.mapper.ReciteRecordMapper;
import com.example.demospringboot.service.ReciteRecordService;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReciteRecordImpl implements ReciteRecordService {
    @Autowired
    private ReciteRecordMapper reciteRecordMapper;



    @Override
    public List<Word> getReviewWordList_al(int bookId, int number, int userId) {
        return null;
    }

    @Override
    public List<Word> getReciteWordList_al(int bookId, int number, int userId) {
        return null;
    }

    @Override
    public Long userHoldTime(int userId, int bookId, LocalDateTime plan_start) {
        QueryWrapper<ReciteRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT lastdate").eq("bookId",bookId).eq("userId",userId).ge("lastdate",plan_start);
        Long cc= reciteRecordMapper.selectCount(queryWrapper);

        return cc;

    }

    @Override
    public Long countReciteWord(int userId, int bookId) {
        QueryWrapper<ReciteRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT word_id").eq("bookId",bookId).eq("userId",userId);
        Long cc= reciteRecordMapper.selectCount(queryWrapper);

        return cc;
    }

    @Override
    public Integer updateWordRecord(int userId, int wordId, int bookId, int rf_judge) {
        //rf_judge   1 为正确 showtime++,2为错误 showtime++ wrongtime++ ,3为太简单 showtime -1
        ReciteRecord reciteRecord =new ReciteRecord();
        LocalDateTime now;
        reciteRecord.setUserid(userId);
        reciteRecord.setWord_id(wordId);
        reciteRecord.setBookid(bookId);
        QueryWrapper<ReciteRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userId).eq("word_id",wordId);
        now=LocalDateTime.now();
        now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        reciteRecord.setLastdate(now);
        System.out.println("-------------------:test----"+reciteRecordMapper.selectCount(queryWrapper));
        if(reciteRecordMapper.selectCount(queryWrapper)==1)//用户已在其他单词本背诵过
        {
            ReciteRecord past =new ReciteRecord();
            past=reciteRecordMapper.selectOne(queryWrapper);
            if (past.getShowtimes()<0)
            {
                System.out.println("已经标记为太简单");//不进行操作
            }else{

                if (rf_judge==3) {
                    reciteRecord.setShowtimes(-1);
                } else if (rf_judge==2) {
                    reciteRecord.setShowtimes(past.getShowtimes()+1);
                    reciteRecord.setWrongtimes(past.getWrongtimes()+1);
                }else {
                    reciteRecord.setShowtimes(past.getShowtimes()+1);
                }
                reciteRecordMapper.update(reciteRecord,queryWrapper);
            }
        }else {//数据库内没有数据，记得添加firsttime
            reciteRecord.setFirstdate(now);
            reciteRecord.setWrongtimes(rf_judge==2?1:0);
            reciteRecord.setShowtimes(1);


            reciteRecordMapper.insert(reciteRecord);
        }



        return null;
    }

    @Override
    public int getOneReviewWord(int userId, int bookId) {
        QueryWrapper<ReciteRecord> queryWrapper = new QueryWrapper<>();
        ReciteRecord reciteRecord;
        queryWrapper.select("word_id").eq("bookId",bookId).last("limit 1").eq("userId",userId).orderByAsc("lastdate");
        if (reciteRecordMapper.selectCount(queryWrapper)==0)
        {
            queryWrapper.clear();
            queryWrapper.orderByAsc("RAND()").last("limit 1");
        }
        reciteRecord= reciteRecordMapper.selectOne(queryWrapper);
        return reciteRecord.getWord_id();
    }

    @Override
    public LocalDateTime getOneReviewLastDate(int userId, int wordId) {
        QueryWrapper<ReciteRecord> queryWrapper = new QueryWrapper<>();
        ReciteRecord reciteRecord;
        queryWrapper.select("lastdate").last("limit 1").eq("userId",userId).eq("word_id",wordId);
        reciteRecord= reciteRecordMapper.selectOne(queryWrapper);
        return reciteRecord.getLastdate();
    }
}
