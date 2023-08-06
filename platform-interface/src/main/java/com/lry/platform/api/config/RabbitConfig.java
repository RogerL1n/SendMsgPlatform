package com.lry.platform.api.config;





import com.lry.platform.common.constants.RabbitMqConsants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lry on  2022/7/14 09:41
 *
 * @author lry
 *
 */
@Configuration
public class RabbitConfig {
    /**
     * 声明一个队列,主要的目的是将通过初步审核的手机号和短信内容封装后发送到该队列,交给策略模块处理
     * @return
     */
    @Bean
    public Queue preSendQueue() {
        return new Queue(RabbitMqConsants.TOPIC_PRE_SEND, true);
    }

    /**
     * 接收策略模块发送的状态报告的消息
     * @return
     */
    @Bean
    public Queue reportQueue() {
        return new Queue(RabbitMqConsants.TOPIC_PUSH_SMS_REPORT, true);
    }

}
