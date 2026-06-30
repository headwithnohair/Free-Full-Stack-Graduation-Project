package com.example.demospringboot.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.Post;
import com.example.demospringboot.mapper.PostMapper;
import com.example.demospringboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service

public class PostImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public List<Post> getPostList(int number) {
        List<Post> list;
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("uploadtime").last("limit "+number);
        list= postMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public ResponseEntity<byte[]> givePic(String src) {
        File file1 = new File(src);
        Path path1 = file1.toPath();
        try {
            byte[] data = Files.readAllBytes(path1);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
        }catch (Exception e){
            System.out.println("post图片加载错误，src：" +src);
            e.printStackTrace();
        }


        return null;
    }

    public String setPic(MultipartFile multipartFile, int index){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String fileName = date +""+index+ ".png";
        String uploadPath="src/main/resources/static/images";
        File file = new File(uploadPath +"/" + fileName);

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            // 获取文件输入流

            inputStream = multipartFile.getInputStream();
            if (!file.exists()) {
                file.createNewFile();
            }
            // 创建输出流
            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            // 写入到创建的临时文件
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 关流
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (outputStream != null) {
                    inputStream.close();
                }
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(file.getPath());
        return file.getPath();
    }

    @Override
    public int setPost(String title, String content, int userId) {
        Post post=new Post();
        LocalDateTime localDateTime=LocalDateTime.now();
        post.setUserid(userId);
        post.setContent(content);
        post.setTitle(title);
        post.setUploadtime(localDateTime);
        post.setChangetime(localDateTime);
        postMapper.insert(post);
        return post.getPostid();
    }

    @Override
    public String setUrlInPost(String url, int postId) {
        System.out.println("！！！！！看这里！！！！"+url);
        Post post;
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("postId",postId);
        post=postMapper.selectOne(queryWrapper);
        List<String> stringList;
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(post.getPicture()==null);
        String rrl=url.replace('\\','/');
        Map<String ,List<String>> map=new HashMap<>();
        if (post.getPicture()==null)
        {   stringList=new ArrayList<>();

            stringList.add(rrl);

            map.put("pic",stringList);
            post.setPicture(map);

        }else {
            stringList= post.getPicture().get("pic");
            System.out.println("！！！！！看这里！！！！"+stringList);
            stringList.add(rrl);
            post.getPicture().put("pic",stringList);
        }
        System.out.println(JSON.toJSONString(stringList));
        postMapper.update(post,queryWrapper);
        System.out.println(post);
        return null;
    }

    @Override
    public List<Post> getPostList(int postId, int number) {
        List<Post> postList;
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.gt("postId",postId).orderByDesc("postId").last("limit " +number);
        postList=postMapper.selectList(queryWrapper);
        return postList;
    }
}
