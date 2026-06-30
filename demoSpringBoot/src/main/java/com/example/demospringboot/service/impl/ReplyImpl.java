package com.example.demospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Reply;
import com.example.demospringboot.mapper.ReplyMapper;
import com.example.demospringboot.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class ReplyImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> getReply(int postId, int number) {

        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("targetid", postId).last("limit " + number).orderByDesc("uploadtime");
        replyMapper.selectList(queryWrapper);
        return replyMapper.selectList(queryWrapper);
    }

    @Override
    public List<Reply> getReply(int postId, int number, int head) {
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("targetid", postId);
        Long i=replyMapper.selectCount(queryWrapper);
        if (head>=i)
        {   List<Reply> list=new ArrayList<>();
            return list;
        }else {
        queryWrapper.eq("targetid", postId).last("limit " +head+" , "+ number).orderByDesc("uploadtime");

        return replyMapper.selectList(queryWrapper);
        }
    }

    @Override
    public String uploadReply(int postId, int userId, String reply) {
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        LocalDateTime localDateTime=LocalDateTime.now();
        Reply reply1=new Reply();
        reply1.setTargetid(postId);
        reply1.setUserid(userId);
        reply1.setReply(reply);
        reply1.setHate(0);
        reply1.setLikes(0);
        reply1.setUpdatetime(localDateTime);
        reply1.setUploadtime(localDateTime);
        replyMapper.insert(reply1);
        return "";
    }

}
