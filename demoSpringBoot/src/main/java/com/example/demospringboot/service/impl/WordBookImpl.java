package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.mapper.WordBookMapper;
import com.example.demospringboot.mapper.WordMapper;
import com.example.demospringboot.service.WordBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WordBookImpl implements WordBookService {
    @Autowired
    private WordBookMapper wordBookMapper;

    @Autowired
    private WordMapper wordMapper;

    @Override
    public List<Word> getReciteWordList_no(int bookId, int number, int userId) {
        QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("word_id", "select word_id from word_book where bookid = " + bookId
                + " and word_id not in  (SELECT  DISTINCT word_id from recite_record where userid=" + userId + ")");
        List<Word> userList = wordMapper.selectList(queryWrapper);
        if (number < userList.size()) {
            userList.subList(number, userList.size()).clear();

        }

        return userList;
    }


    @Override
    public List<Word> getReviewWordList_no(int bookId, int number, int userId,List<Word> userList) {
        int ganga=userList.size();
        if (number < userList.size()) {//????
            userList.subList(number, userList.size()).clear();

        } else if (number > userList.size()) {
            QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
            queryWrapper.inSql("word_id", "SELECT  DISTINCT word_id from recite_record where userid= " +
                    userId + " Order by lastdate ASC ");// LIMIT (number-userList.size())
            //升序，离现在时间越近越大
            List<Word> test = wordMapper.selectList(queryWrapper);//这里排序按照id排
            for (int i=0;i<(number-ganga)&&i<test.size();i++)
            {
                userList.add(test.get(i));
            }
            System.out.println("现在userlist有：个"+userList.size());
        }

        return userList;
    }

    @Override
    public List<Word> getAllWordList(int bookId, int limit, List<Word> wordList) {
        QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("word_id", "select word_id from word_book where bookid = " + bookId).last(" LIMIT "+limit);

        List<Word> test= wordMapper.selectList(queryWrapper);
        for (int i=0;i<limit;i++)
        {
            wordList.add(test.get(i));

        }
        return wordList;
    }
}

