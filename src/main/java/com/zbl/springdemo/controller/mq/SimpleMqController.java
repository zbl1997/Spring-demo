package com.zbl.springdemo.controller.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/28 13:59
 * @Version
 **/

@RestController
public class SimpleMqController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/helloMQ")
    public void helloMQ() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);//消息持久化

        Message message = new Message("hello".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("simpleQueue",message);
    }
}
