package com.zbl.springdemo.mq.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/28 13:50
 * @Version
 **/

@Component
public class SimpleListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            System.out.println(this.getClass().getName() + " 我拿到消息了！" );
            System.out.println("Body: " + new String(message.getBody()));
            System.out.println("Message: " + JSON.toJSONString(message));

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
