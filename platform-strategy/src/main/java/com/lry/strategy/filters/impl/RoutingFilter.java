package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lry on  2022/7/21 10:55
 *
 * @author lry
 *   
 */
@Component
public class RoutingFilter implements FilterChain {
    private CacheService cacheService;
    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    private AmqpTemplate amqpTemplate;
    @Autowired
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        //这个过滤器的目的是在所有的其他过滤器执行完成之后执行的,也就是说他的位置在最后一个
        //作用是用于添加限流,将信息转发到网关中
        //1 添加限流
        System.err.println("在路由过滤器中添加限流数据");
        long max = System.currentTimeMillis();
        cacheService.add2Zset(CacheConstants.CACHE_PREFIX_SMS_LIMIT + standard_submit.getClientID() + standard_submit.getDestMobile(), max + "", max);
        //将信息转发到对应的网关和搜索中
        //我们的网关会有很多,分别处理不同的通道的短信, 所以我们需要知道当前应该发送到哪个网关
        //网关按照我们的约定会监听xxxx_通道id的队列,我们在这边唯一需要获取的就是通道的id
        //通过获取到的通道的id 拼接队列名字的字符串,然后发送到指定队列即可
        String extendnumber = (String) cacheService.hGet(CacheConstants.CACHE_PREFIX_ROUTER + standard_submit.getClientID(), "extendnumber");
        standard_submit.setSrcNumber(extendnumber);//设置扩展号, 比如发送短信的通道号为106998 ,扩展号是9988 ,那么用户最终收到的短信的发送号码就是1069989988
        Object channelid = cacheService.hGet(CacheConstants.CACHE_PREFIX_ROUTER + standard_submit.getClientID(), "channelid");
        //向指定网关的队列中发送我们的短信内容数据
        amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_SMS_GATEWAY+channelid,standard_submit);
        return true;
    }
}
