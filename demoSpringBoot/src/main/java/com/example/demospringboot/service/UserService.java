package com.example.demospringboot.service;

import com.example.demospringboot.domain.User;
import com.example.demospringboot.domain.Word;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface UserService {

    String wxHasUser(String loginCode,String jsondata);//有则返还userId,没有返还null

    int wxLogin(String wxopenId);

    User getUserInfo(int userId);

    String setUserInfo(User user);
    String saveUserIcon(String url,int userId);
    String  getUserIcon(MultipartFile multipartFile);

    ResponseEntity<byte[]> giveWxIcon(int userId);

}
