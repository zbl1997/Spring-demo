package com.zbl.springdemo.mq.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/29 10:37
 * @Version
 **/

@Component
public class AnnotationsListener {

    @RabbitListener(bindings =
                        @QueueBinding(value = @Queue(value = "test_Annotation_queue",
                                                     declare = "true"),
                                     exchange = @Exchange(value = "test_Annotation_exchange",
                                                          ignoreDeclarationExceptions = "true",
                                                          type = ExchangeTypes.TOPIC),
                                     key = {"#.#"}))
    public void onMessage(Message message, Channel channel) throws IOException {
        System.out.println("收到 " + JSON.toJSONString(message));
        channel.basicAck(1000L, true);
    }
}
