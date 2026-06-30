package com.example.demospringboot.service;

import com.example.demospringboot.domain.Book;

import java.util.List;

public interface BookService {
    String findBookName(Integer bookId);
    Integer getBookWordCount(Integer bookId);
    List<Book> getBookList(int number);
}
