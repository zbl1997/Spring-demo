package com.zbl.springdemo.config;

import com.zbl.springdemo.mq.listener.SimpleListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/28 13:37
 * @Version
 **/

@Configuration
public class RabbitMQConfig {
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private SimpleListener simpleListener;

    /**
     * exchange
     * @return
     */

    @Bean("testDirectExchange")
    public DirectExchange getDirectExchange() {
        return new DirectExchange("ZBL_TEST_DIRECT_EXCHANGE");
    }

    @Bean("testTopicExchange")
    public TopicExchange getTopicExchange() {
        return new TopicExchange("ZBL_TEST_TOPIC_EXCHANGE");
    }

    @Bean("testFanoutExchange")
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange("ZBL_TEST_FANOUT_EXCHANGE");
    }

    /**
     * queue
     */

    @Bean("simpleQueue")
    public Queue getMsgQueue() {
        return new Queue("SIMPLE_QUEUE");
    }

    /**
     * bind
     */

    @Bean("simpleMessageListenerContainer")
    public SimpleMessageListenerContainer getSimpleMessageListenerContainer() {
        SimpleMessageListenerContainer sm = new SimpleMessageListenerContainer(connectionFactory);
        sm.setQueueNames("simpleQueue");
        sm.setConcurrentConsumers(1);
        sm.setMaxConcurrentConsumers(10);
        sm.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        sm.setMessageListener(simpleListener);

        return sm;
    }
}
