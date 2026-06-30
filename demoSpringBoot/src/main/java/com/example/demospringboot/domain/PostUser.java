package com.example.demospringboot.domain;

import lombok.Data;

import java.util.Map;

@Data
public class PostUser {
    private Integer userId;
    private String userIcon;
    private String userName;
    private Post post;
    private String actiontime;
}
