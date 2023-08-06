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

public interface BlackNumUpdateInput {
    @Input(RabbitMqConsants.TOPIC_BLACKNUM_UPDATE)
    SubscribableChannel subscribable_blacknum_channel();
}
