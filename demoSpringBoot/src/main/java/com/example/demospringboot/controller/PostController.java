package com.example.demospringboot.controller;

import com.alibaba.fastjson2.JSON;
import com.example.demospringboot.domain.*;
import com.example.demospringboot.service.PostService;
import com.example.demospringboot.service.ReplyService;
import com.example.demospringboot.service.UserService;
import com.example.demospringboot.tools.TimeCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("post")
@RestController
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/test")
    public String test(){
        List<PostUser> postUserList=new ArrayList<>();
        List<Post>  list;
        PostUser postUser;
        User user;
        int listsize;
        list =postService.getPostList(5);
        listsize=list.size();
        for (int i=0;i<listsize;i++)
        {   postUser=new PostUser();
            user=userService.getUserInfo(list.get(i).getUserid());
            String yyy=TimeCountUtil.format(list.get(i).getUploadtime());
            postUser.setActiontime(yyy);
            postUser.setPost(list.get(i));
            ///
            postUser.setUserId(user.getUserid());
            postUser.setUserName(user.getNickname());
            postUser.setUserIcon(user.getUsericon());

            postUserList.add(postUser);
        }
        return JSON.toJSONString(postUserList) ;
    }

    @GetMapping("/givePic")
    public ResponseEntity<byte[]> givePic(String src){

        return postService.givePic(src);
    }

    @GetMapping("/getReply")
    public String getReply(int postId,int number){
        List<ReplyUser> replyUsers=new ArrayList<>();
        ReplyUser replyUser;
        User user;
        List<Reply> replyList;
        replyList=replyService.getReply(postId,number);
        for (int i=0;i<replyList.size();i++)
        {   replyUser=new ReplyUser();
            user=userService.getUserInfo(replyList.get(i).getUserid());
            String yyy=TimeCountUtil.format(replyList.get(i).getUploadtime());
            replyUser.setActiontime(yyy);
            replyUser.setReply(replyList.get(i));
            replyUser.setUserId(user.getUserid());
            replyUser.setUserName(user.getNickname());
            replyUsers.add(replyUser);
        }
        return JSON.toJSONString(replyUsers);
    }

    @GetMapping("/pulldownReply")
    public String pulldownReply(int postId,int number,int head)
    {   List<ReplyUser> replyUsers=new ArrayList<>();
        ReplyUser replyUser;
        User user;
        List<Reply> replyList;
        replyList= replyService.getReply(postId,number,head);
        for (int i=0;i<replyList.size();i++)
        {   replyUser=new ReplyUser();
            user=userService.getUserInfo(replyList.get(i).getUserid());
            String yyy=TimeCountUtil.format(replyList.get(i).getUploadtime());
            replyUser.setActiontime(yyy);
            replyUser.setReply(replyList.get(i));
            replyUser.setUserId(user.getUserid());
            replyUser.setUserName(user.getNickname());
            replyUsers.add(replyUser);
        }
        return JSON.toJSONString(replyUsers);
    }
    @GetMapping("/uploadReply")
    public String uploadReply(int postId,int userId,String reply)
    {
        replyService.uploadReply(postId,userId,reply);
        return null;
    }

    @PostMapping("/postPicReciver")
    public  String postPicReciver(MultipartFile multipartFile,int index,int postId) {
        String url=postService.setPic(multipartFile,index);
        postService.setUrlInPost(url,postId);
        return  url;
    }
    @GetMapping("/postReciver")
    public  String postReciver(String title,String content,int userId)
    {
        Integer i=postService.setPost(title,content,userId);
        return i.toString();
    }
    @GetMapping("/refreshPost")
    public  String refreshPost(int postId,int number)
    {
        List<PostUser> postUserList=new ArrayList<>();
        List<Post>  list;
        PostUser postUser;
        User user;
        int listsize;
        list =postService.getPostList(postId,number);
        listsize=list.size();
        for (int i=0;i<listsize;i++)
        {   postUser=new PostUser();
            user=userService.getUserInfo(list.get(i).getUserid());
            String yyy=TimeCountUtil.format(list.get(i).getUploadtime());
            postUser.setActiontime(yyy);
            postUser.setPost(list.get(i));
            ///
            postUser.setUserId(user.getUserid());
            postUser.setUserName(user.getNickname());
            postUser.setUserIcon(user.getUsericon());
            postUserList.add(postUser);
        }
        return JSON.toJSONString(postUserList) ;

    }
}
