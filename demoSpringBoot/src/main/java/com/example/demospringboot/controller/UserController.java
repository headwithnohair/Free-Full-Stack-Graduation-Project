package com.example.demospringboot.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.User;

import com.example.demospringboot.mapper.UserMapper;
import com.example.demospringboot.service.UserPlanService;
import com.example.demospringboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPlanService userPlanService;

    @GetMapping("login")
    public String login(String username,String password){
        User user;
        User test=new User();
        test.setUsername(username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
       user =  userMapper.selectOne(queryWrapper);
        if(user==null)
        {
            return  "-1";
        }

        if (Objects.equals(password, user.getPassword())){

            return user.getUserid().toString();//userid
        }
    return "-1";

    }
    @GetMapping("register")//cs
    public  String register( Integer userid,String username,String password){
//        log.info("username:{}",username);
//        log.info("userid:{}",userid);
//        log.info("password:{}",password);
    if (StringUtils.isEmpty(userid)){
        return "id is null";

    }
    if(StringUtils.isEmpty(username)){

        return "name is null";
    }
    if(StringUtils.isEmpty(password)){

            return "password is null";
    }
    User res=new User();
    res.setUsername(username);
    res.setUserid(userid);
    res.setPassword(password);
    int resultCount=userMapper.insert(res);
    if(resultCount==0)
    {
        return "failed";

    }
        return  "居然成功了";
    }



    @GetMapping("wxcheckLogin")
    public String wxcheckLogin(String loginCode){
        String jsondata=new String();
        int t ;
        String code;//session_key,openid,uniId
        jsondata=userService.wxHasUser(loginCode,jsondata);
        System.out.println("/////////////////////////////");
        System.out.println(jsondata);
        JSONObject jsonObject= JSONObject.parseObject(jsondata);
        t=userService.wxLogin(jsonObject.get("openid").toString());
        userPlanService.setdefaultPlan(t);
        return String.valueOf(t);
    }

    @GetMapping("wxGetUserInfo")
    public String wxGetUserInfo(int userId){
        User user;
        user=userService.getUserInfo(userId);//
        return JSON.toJSONString(user);
    }

    @PostMapping ("wxChangeUserInfo")
    public String wxChangeUserInfo(@RequestBody User user){

        System.out.println("???"+user);
      //  userService.saveUserIcon(user.getUsericon());
        userService.setUserInfo(user);
        return "asdasd";
    }
    @GetMapping("/test")
    public ResponseEntity <byte[]> test() throws Exception {
      return  null;
        //return path;
    }

    @PostMapping ("/getUserIcon")
    public String getIcon(MultipartFile multipartFile,int userId) throws Exception {
            String iconpath=userService.getUserIcon(multipartFile);
            System.out.println(userId);
            userService.saveUserIcon(iconpath,userId);
        return "donn";
    }

    @GetMapping("/gaveIcon")
    public ResponseEntity <byte[]> getIcon(int userId){

        return userService.giveWxIcon(userId);
    }
}
