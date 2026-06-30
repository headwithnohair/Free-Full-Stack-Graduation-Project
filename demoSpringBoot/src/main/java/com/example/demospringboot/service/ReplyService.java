package com.example.demospringboot.service;

import com.example.demospringboot.domain.Reply;

import java.util.List;

public interface ReplyService {

    List<Reply> getReply(int postId,int number);

    List<Reply> getReply(int postId,int number,int head);

    String uploadReply(int postId,int userId,String reply);

}
