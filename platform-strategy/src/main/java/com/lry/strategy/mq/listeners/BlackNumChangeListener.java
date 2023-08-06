package com.lry.strategy.mq.listeners;


   


import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.strategy.events.BlackChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by lry on  2022/7/15 15:53
 *
 * @author lry
 *   
 */
@Component
public class BlackNumChangeListener {
    private ApplicationContext context;
    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 接收来自于更新黑名单的交换机的消息
     * @param message
     */
    @StreamListener(RabbitMqConsants.TOPIC_BLACKNUM_UPDATE)
    public void onMessage(String message) {
        System.err.println("收到更新黑名单的消息了");
        context.publishEvent(new BlackChangeEvent());
    }
}
