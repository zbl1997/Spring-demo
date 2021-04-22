package com.zbl.springdemo.event.listener;

import com.zbl.springdemo.event.HelloEvent;
import com.zbl.springdemo.utils.Threads;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @Description 基于注解
 * @Author ZhuBoLin
 * @Data 2021/4/22 17:05
 * @Version
 **/

@Component
public class HelloAnnotationListener {


    @EventListener(HelloEvent.class)
    public void listenHelloEventACK(HelloEvent helloEvent) {
        Threads.sleep(1000L);
        System.out.println(helloEvent.getCode() + ", 我知道啦 ,time:" + new SimpleDateFormat("HH:mm:ss SSS").format(new Date()));
        Threads.sleep(2000L);
    }

    @EventListener(HelloEvent.class)
    public void listenHelloEventHandle(HelloEvent helloEvent) {
        Threads.sleep(1000L);
        System.out.println(helloEvent.getCode() + ", 正在进行 ,time:" + new SimpleDateFormat("HH:mm:ss SSS").format(new Date()));
        Threads.sleep(2000L);
    }
}
