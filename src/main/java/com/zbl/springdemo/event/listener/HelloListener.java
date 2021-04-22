package com.zbl.springdemo.event.listener;

import com.zbl.springdemo.event.HelloEvent;
import com.zbl.springdemo.utils.Threads;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 基于类
 * @Author ZhuBoLin
 * @Data 2021/4/22 16:50
 * @Version
 **/

@Service
public class HelloListener implements ApplicationListener<HelloEvent> {

    @Override
    public void onApplicationEvent(HelloEvent helloEvent) {
        System.out.println(helloEvent.getCode() + " 绝不姑息, time:" + System.currentTimeMillis());
        Threads.sleep();
    }

}
