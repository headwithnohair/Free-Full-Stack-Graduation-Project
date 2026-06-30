package com.example.demospringboot;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoSpringBootApplicationTests.class)
@MapperScan(basePackages ="com.example.demospringboot.mapper")
class DemoSpringBootApplicationTests {

    @Test
    void contextLoads() {
    }

}
