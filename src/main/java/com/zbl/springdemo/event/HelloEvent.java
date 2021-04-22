package com.zbl.springdemo.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/22 16:47
 * @Version
 **/

public class HelloEvent extends ApplicationEvent {
    private String code;

    public HelloEvent(Object source, String code) {
        super(source);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
