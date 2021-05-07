package com.zbl.springdemo.config;

import com.zbl.springdemo.mq.listener.MyAckReceiver;
import com.zbl.springdemo.mq.listener.SimpleListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private MyAckReceiver myAckReceiver;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
//        rabbitTemplate.setChannelTransacted(true);//开启事务
        //消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack){//消息发送失败
                    System.out.println("消息发送失败，原因为：" + cause);
                    return;
                }
                    //消息发送成功
                    System.out.println("消息发送成功");
            }
        });

        rabbitTemplate.setMandatory(true);//开启监听回调，监听路由失败的消息
        //消息是否成功被路由到队列，没有路由到队列时会收到回调（原setReturnCallback在2.0版本已过期）
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                    System.out.println("收到未路由到队列的回调消息：" + new String(returnedMessage.getMessage().getBody()));
            }
        });
        return rabbitTemplate;
    }


    /**
     * 手动确认ack
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        //设置一个队列
        container.setQueueNames("simpleQueue");

        container.setMessageListener(myAckReceiver);

        return container;
    }
    /**
     * exchange
     * @return
     */

    @Bean("testDirectExchange")
    public DirectExchange getDirectExchange() {
        Map map = new HashMap<String, String>();
        //alternate-exchange: 配置备份交换机，消息接收成功但路由失败，发送到此队列。
        map.put("alternate-exchange", "ZBL_TEST_TOPIC_EXCHANGE");
        return new DirectExchange("ZBL_TEST_DIRECT_EXCHANGE",false,false,map);
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

    @Bean
    public Queue getTtlQueue() {
        Map<String, Object> map = new HashMap<String, Object>();
        //x-max-length：队列中的最大消息数。
        //x-max-length-bytes：队列的最大容量（bytes）。
        //overflow：队列溢出之后的策略 drop-head - 丢弃队列头部消息(集群)
        //x-dead-letter-exchange：队列的死信交换机。
        //x-dead-letter-routing-key：死信交换机的路由键。
        //x-single-active-consumer：true/false。表示是否最多只允许一个消费者消费
        //x-queue-master-locator：选择主节点策略，保证消息的 FIFO。min-masters- 托管最小数量的绑定主机的节点；client-local- 选择声明的队列已经连接到客户端的节点；random- 随机选择一个节点
        map.put("x-message-ttl", 5000);//队列中所有消息5秒后过期，到队列头部时删除
        map.put("x-expires", 100000);//队列闲置10秒后立即被删除
        //参数1-name：队列名称
        //参数2-durable：是否持久化
        //参数3-exclusive:是否排他。设置为true时，则该队列只对声明当前队列的连接(Connection)可用,一旦连接断开，队列自动被删除
        //参数4-autoDelete：是否自动删除。前提是必须要至少有一个消费者先连上当前队列，然后当所有消费者都断开连接之后，队列自动被删除
        return new Queue("LONGLY_WOLF_TTL_QUEUE",false,false,false,map);
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
