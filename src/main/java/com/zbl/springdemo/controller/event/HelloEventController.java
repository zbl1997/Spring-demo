package com.zbl.springdemo.controller.event;

import com.zbl.springdemo.event.HelloEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/22 16:48
 * @Version
 **/

@RestController
public class HelloEventController {
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/hello")
    public String hello() {
        applicationContext.publishEvent(new HelloEvent(this, "德莱厄斯"));
        return "欢迎来到德莱联盟";
    }
}
