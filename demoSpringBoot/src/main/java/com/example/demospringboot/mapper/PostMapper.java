package com.example.demospringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demospringboot.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper  extends BaseMapper<Post> {
}
