package com.zbl.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/22 17:21
 * @Version
 **/

@Configuration
public class EventConfig {

    // 事件多播器
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster getApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster se = new SimpleApplicationEventMulticaster();

        // 给个线程池
        se.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return se;
    }
}
