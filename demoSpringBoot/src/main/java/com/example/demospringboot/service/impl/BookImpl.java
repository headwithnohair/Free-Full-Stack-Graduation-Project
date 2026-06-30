package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Book;
import com.example.demospringboot.mapper.BookMapper;
import com.example.demospringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public String findBookName(Integer bookId) {
        QueryWrapper<Book> tt=new QueryWrapper<Book>();
        tt.eq("bookId",bookId);

        return bookMapper.selectOne(tt).getBookname();
    }

    @Override
    public Integer getBookWordCount(Integer bookId) {
        Integer tta;
        QueryWrapper<Book> tt=new QueryWrapper<Book>();
        tt.eq("bookId",bookId).select("wordCount");
        tta=bookMapper.selectOne(tt).getWordCount();
        return tta;

    }

    @Override
    public List<Book> getBookList(int number) {
        List<Book> bookList;
        QueryWrapper<Book> tt=new QueryWrapper<Book>();
        tt.last("limit "+number);
        bookList=bookMapper.selectList(tt);
        return bookList;
    }
}
