package com.example.demospringboot.service;

import com.example.demospringboot.domain.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

     List<Post> getPostList(int number);

    ResponseEntity<byte[]> givePic(String src);

    String setPic(MultipartFile multipartFile, int postId);
    int setPost(String title,String content,int userId);

    String setUrlInPost(String url,int postId);

    List<Post> getPostList(int postId,int number);
}
