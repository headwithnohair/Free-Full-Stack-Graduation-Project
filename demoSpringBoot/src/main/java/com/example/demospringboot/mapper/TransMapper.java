package com.example.demospringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.example.demospringboot.domain.Trans;
import org.springframework.stereotype.Repository;

@Repository
public interface TransMapper extends BaseMapper<Trans> {
}
