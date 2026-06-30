package com.example.demospringboot.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

public class DayBookHandler extends JacksonTypeHandler {

    public DayBookHandler(Class<?> type) {
        super(type);
    }
}
