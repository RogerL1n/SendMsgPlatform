package com.lry.strategy.mq.inoutput;
   


import com.lry.platform.common.constants.RabbitMqConsants;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by lry on  2022/7/15 15:51
 *
 * @author lry
 *   
 */

public interface LimitUpdateInput {
    @Input(RabbitMqConsants.TOPIC_LIMIT_UPDATE)
    SubscribableChannel subscribable_limit_channel();
}
