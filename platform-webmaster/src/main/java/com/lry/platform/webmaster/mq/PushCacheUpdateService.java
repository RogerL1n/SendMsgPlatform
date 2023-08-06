package com.lry.platform.webmaster.mq;


   


import com.lry.platform.common.constants.RabbitMqConsants;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 需要banding到启动类上
 * 这是一个服务 可以注入
 */

public interface PushCacheUpdateService {

    @Output(RabbitMqConsants.TOPIC_BLACKNUM_UPDATE)//更新黑名单策略
    MessageChannel updateBlackChannel();

    @Output(RabbitMqConsants.TOPIC_DIRTYWORDS_UPDATE)//更新敏感词策略
    MessageChannel updateDirtyWordsChannel();

     @Output(RabbitMqConsants.TOPIC_EXECFILTERS_UPDATE)// 更新过滤器策略
     MessageChannel updateFiltersChannel();

     @Output(RabbitMqConsants.TOPIC_LIMIT_UPDATE)// 更新限流策略
     MessageChannel updateLimitsChannel();

    @Output(RabbitMqConsants.TOPIC_SEARCHPARAMS_UPDATE)// 更新查询参数规则
    MessageChannel updateSearchParamsChannel();
}
