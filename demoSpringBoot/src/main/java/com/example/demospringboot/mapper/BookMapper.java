package com.example.demospringboot.mapper;

import com.example.demospringboot.domain.Book;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Repository
public interface BookMapper extends BaseMapper<Book> {

}
