package com.example.demospringboot.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.User;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.mapper.UserMapper;
import com.example.demospringboot.service.UserService;
import com.example.demospringboot.tools.RandomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public String wxHasUser(String loginCode ,String jsondata) {
        String appid="appid=YOUR-APPID-HERE"+"&";
        String secret= "secret=YOUR-APPsecret-HERE"+"&";
        String js_code="js_code="+loginCode+"&";
        String grant_type="grant_type=authorization_code";

        try {
            URL url = new URL("https://api.weixin.qq.com/sns/jscode2session");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");  //设置编码语言


            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写

            conn.setReadTimeout(3*1000);//设置读取超时时间
            conn.setConnectTimeout(3*1000);//设置连接超时时间

            conn.connect();

            out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流
            out.print(appid+secret+js_code+grant_type);//发送请求参数即数据

            out.flush();//缓冲数据
            out.close();

            int responseCode = conn.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                System.out.println(responseCode);
//            }
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {

                str=new String(str.getBytes(),"UTF-8");//解决中文乱码问题
                System.out.println("小程序js_code获取成功："+str);
                jsondata =new String(str);
            }

            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }



        return jsondata;
    }

    @Override
    public int wxLogin(String wxopenId) {
        User user=new User();;
        int temp=-1;
        Long count;
        String username;
        String password;
        System.out.println(wxopenId);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_openid",wxopenId);
        count=userMapper.selectCount(queryWrapper);
        if (count==1)//新用户？如何注册？
        {
            user=userMapper.selectOne(queryWrapper);
            temp=user.getUserid();
        }
        else if(count==0 ){
            user=new User();
            defaultUser(user);
            queryWrapper.clear();
            user.setWx_openid(wxopenId);
            userMapper.insert(user);//insert 后自动填充userid！！！爽
        }

        System.out.println(user);
        return user.getUserid();
    }
    public  User defaultUser(User user){//username 或者 password
        List<User> list;
        List<String> olduser=new ArrayList<>();
        List<String> outPut;

        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("username");
        list=userMapper.selectList(queryWrapper1);
        for (User  tt    : list)
        {
            olduser.add(tt.getUsername());
        }
        user.setUsername(RandomUser.getUserIds(olduser,1).get(0));
        user.setPassword(RandomUser.getPasswords(1,5).get(0));
        user.setHate(0);
        user.setSigntime(LocalDateTime.now());
        user.setFans(0);
        user.setLikes(0);
        user.setNickname("用户名");
        user.setUsericon("");
        user.setPostcount(0);
        return  user;
         }

    @Override
    public User getUserInfo(int userId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user;
        queryWrapper.eq("userId",userId);
        user=userMapper.selectOne(queryWrapper);

    return user;
    }


    public String setUserInfo(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",user.getUserid());
        user.setUsericon(null);
        int resultCount=userMapper.update(user,queryWrapper);
        if(resultCount==0)
        {
            return "failed";

        }
        return "done";
    }

    @Override
    public String saveUserIcon(String pictureUrl,int userId) {//存入数据库
        User user=new User();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        user.setUsericon(pictureUrl);
        int resultCount=userMapper.update(user,queryWrapper);
        if(resultCount==0)
        {
            return "failed";
        }
        return "done";
    }

    @Override
    public String getUserIcon(MultipartFile multipartFile) {//收到wx的传参，下载图片到本地数据，保留相对路径交给saveUserIcon存入数据库
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String fileName = date + ".png";
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
    public ResponseEntity<byte[]> giveWxIcon(int userId) {
        User user=new User();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        user=userMapper.selectOne(queryWrapper);
        String iconUrl=user.getUsericon();
        System.out.println(iconUrl);
        File file1 = new File(iconUrl);
        Path path1 = file1.toPath();
        try {
            byte[] data = Files.readAllBytes(path1);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
        }catch (Exception e){
            System.out.println("头像加载错误，userID：" +user.getUserid());
            e.printStackTrace();
        }


        return null;
    }
}
