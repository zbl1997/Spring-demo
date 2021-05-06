package com.zbl.springdemo;

import com.zbl.springdemo.controller.event.HelloEventController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDemoApplicationTests {
    @Autowired
    private HelloEventController helloEventController;

    @Test
    void contextLoads() {
    }

}
