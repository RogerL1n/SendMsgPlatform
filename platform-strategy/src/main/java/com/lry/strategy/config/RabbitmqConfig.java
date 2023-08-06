package com.lry.strategy.config;


   


import com.lry.platform.common.constants.RabbitMqConsants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lry on  2022/7/14 11:01
 *
 * @author lry
 *   
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 接收消息的队列
     * @return
     */
    @Bean
    public Queue preSendQueue() {
        return new Queue(RabbitMqConsants.TOPIC_PRE_SEND, true);
    }

}
