package com.example.demospringboot.domain;

import lombok.Data;

@Data
public class ReplyUser {
    private Integer userId;
    private String userName;
    private Reply reply;
    private String actiontime;
}
