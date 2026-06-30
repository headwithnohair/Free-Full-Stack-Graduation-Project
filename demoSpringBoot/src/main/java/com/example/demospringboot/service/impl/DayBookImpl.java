package com.example.demospringboot.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demospringboot.domain.DayBook;
import com.example.demospringboot.domain.Word;
import com.example.demospringboot.domain.WordData;
import com.example.demospringboot.mapper.DayBookMapper;
import com.example.demospringboot.service.DayBookService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DayBookImpl implements DayBookService {
    @Autowired
    private DayBookMapper dayBookMapper;
    @Override
    public Boolean checkUserData(Integer userId) {//确认用户是否在今日来过小程序
        DayBook temp;
        QueryWrapper<DayBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("UserId",userId);
        temp=dayBookMapper.selectOne(queryWrapper);
        LocalDateTime dateTime = LocalDateTime.now();

        dateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0, 0);
        //转型再转回来也行

        if(temp!=null&&dateTime.equals(temp.getStartDate()))
        {
            return  true;
        }else {
            return  false;
        }

    }

    @Override
    public String insertUserData(WordData wordData) {//从前端获取的wordData做处理，存入数据库，可以当日多次存入
        DayBook dayBook=new DayBook();
        int reult;
        dayBook.setUserId(wordData.getUserPlan().getUserid());
        List<Integer> tt=new ArrayList<>();
        Map<String, List<Integer>> temp=new HashMap<>();

        for (Word word:wordData.getNoRecite())
        {
                tt.add(word.getWord_id());

        }
        temp.put("noRecite",tt.stream().collect(Collectors.toList()));
        tt.clear();

        for (Word word:wordData.getNoReview())
        {
            tt.add(word.getWord_id());

        }
        temp.put("noReview",tt.stream().collect(Collectors.toList()));
        tt.clear();
        temp.put("wrongList",tt);
        dayBook.setNoRecite(temp );
        LocalDateTime dateTime = LocalDateTime.now();
        dayBook.setStartDate(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(),
                dateTime.getDayOfMonth(), 0, 0, 0));

        QueryWrapper<DayBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("UserId",dayBook.getUserId());
        if(dayBookMapper.selectCount(queryWrapper)==0)
        {
            reult= dayBookMapper.insert(dayBook);
        }else {
            reult= dayBookMapper.update(dayBook,queryWrapper);
        }
        if(reult==1)
        {
            System.out.println("用户id："+dayBook.getUserId()+" 今日数据更新成功！");
        }else {
            System.out.println("用户id："+dayBook.getUserId()+" 今日数据更新error！reult:  "+reult );
        }
        return null;
    }

    @Override
    public DayBook getDayBook(DayBook dayBook) {//传对象，直接改值即可
        QueryWrapper<DayBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("UserId",dayBook.getUserId());
        dayBook=dayBookMapper.selectOne(queryWrapper);
        return dayBook;
    }

    @Override
    public Integer listUpdater(int wordId,int rf_judge,int judge,int userId) {//
        QueryWrapper<DayBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        DayBook dayBook;
        dayBook=dayBookMapper.selectOne(queryWrapper);
        if (dayBook.getNoRecite().get("wrongList")==null)//指在map内没有wrongList字符串对应的list
        {
            dayBook.getNoRecite().put("wrongList",new ArrayList<>());
        }

        updateJudge(dayBook,rf_judge,judge,wordId);

        dayBookMapper.update(dayBook,queryWrapper);
        return null;
    }

    @Override
    public String endWrongList(int userId) {
        QueryWrapper<DayBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        DayBook dayBook;
        dayBook=dayBookMapper.selectOne(queryWrapper);
        List noReview=dayBook.getNoRecite().get("noReview");
        List wrongList=dayBook.getNoRecite().get("wrongList");
        int cite=dayBook.getNoRecite().get("noRecite").size();
        int view=noReview.size();
        int list=wrongList.size();
        if(cite!=0&&!(noReview.containsAll(wrongList)&&wrongList.containsAll(noReview)))//仍有没有背诵或复习的单词
        {
            return JSON.toJSONString("keep");
        } else if (list>0||view>0) { //示意前端进入wronglist页面
            return JSON.toJSONString("startWrongList");
        }else {
        return "complete";}
    }

    @Override
    public int nextWrongword(int userId) {
        QueryWrapper<DayBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        DayBook dayBook;
        List temp;
        dayBook=dayBookMapper.selectOne(queryWrapper);
        temp=dayBook.getNoRecite().get("noReview");
        if (temp.size()!=0)
        {
            return (int) temp.get(0);
        }else
        return (-1);
    }


    public String updateJudge(DayBook dayBook,int rf_judge,int judge,int wordId) {//judge=1 背诵,2 复习
        int count= Collections.frequency(dayBook.getNoRecite().get("wrongList"),wordId );
        List<Integer> list;
        list=dayBook.getNoRecite().get("wrongList");
        if (judge==1)
        {
            if (rf_judge==2){   //在背诵中不会的词
                list.add(wordId);
            } else if (rf_judge==3) {
                dayBook.getNoRecite().get("noReview").remove(Integer.valueOf(wordId));
                for (int i=list.size()-1;i>=0;i--)
                {
                    if (list.get(i)==wordId)
                        list.remove(i);
                }
            }
            dayBook.getNoRecite().get("noRecite").remove(Integer.valueOf(wordId));
        } else if (judge==2) {
            if (rf_judge==1){
                if (list.contains(wordId)==false){
                    dayBook.getNoRecite().get("noReview").remove(Integer.valueOf(wordId));
                }else {
                    list.remove(Integer.valueOf(wordId));
                }
            } else if (rf_judge==2) {//错两次以上不记录

                if (count<2){
                    list.add(Integer.valueOf(wordId));
                }
            } else if (rf_judge==3) {
                dayBook.getNoRecite().get("noReview").remove(Integer.valueOf(wordId));
                for (int i=list.size()-1;i>=0;i--)
                {
                    if (list.get(i)==wordId)
                        list.remove(i);
                }

            }

        }

        return null;
    }
}
