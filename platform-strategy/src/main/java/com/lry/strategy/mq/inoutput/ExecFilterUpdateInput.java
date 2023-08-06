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

public interface ExecFilterUpdateInput {
    @Input(RabbitMqConsants.TOPIC_EXECFILTERS_UPDATE)
    SubscribableChannel subscribable_filters_channel();
}
