package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.DayBook;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.mapper.WordMapper;
import com.example.demospringboot.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class WordImpl implements WordService {
    @Autowired
    private WordMapper wordMapper;
    @Override
    public List<Word> getWordDetail(List<Integer> tt) {
        List<Word> wordList=new ArrayList<>();
        if(tt!=null){

        QueryWrapper<Word> queryWrapper = new QueryWrapper<>();

        for (Integer wordId : tt){
            queryWrapper.eq("word_id",wordId);
            wordList.add(wordMapper.selectOne(queryWrapper));
            queryWrapper.clear();
        }
        }
        //System.out.println(wordList);
        return wordList;
    }

    @Override
    public List<Word> obscureSreachWord(String word,int limit) {
        QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("word_name",word).last("Limit "+limit);
        List<Word> temp;
        temp=wordMapper.selectList(queryWrapper);
        return temp;
    }


}
