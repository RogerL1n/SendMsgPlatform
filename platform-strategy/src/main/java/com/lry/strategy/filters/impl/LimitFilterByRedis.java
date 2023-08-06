package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.InterfaceExceptionDict;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.service.SendReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * Created by lry on  2022/7/19 11:17
 * 这个过滤器的主要作用是限流的
 * 限制同一个客户针对同一个号码最多能发送多少次短信
 * 比如一分钟最多发2次, 一小时5次 一天 10次
 *
 * @author lry
 *   
 */
//@Component
public class LimitFilterByRedis implements FilterChain {
    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    private SendReportService sendReportService;

    @Autowired
    public void setSendReportService(SendReportService sendReportService) {
        this.sendReportService = sendReportService;
    }


    @Override
    public boolean dealSms(Standard_Submit standard_submit) {

        boolean result = true;
        //获取到当前客户针对这个号码已经发送了多少次短信,比如放到redis, 如果放到redis
        //然后进行比较
        int clientID = standard_submit.getClientID();
        String destMobile = standard_submit.getDestMobile();

        //先获取限流的策略
        Set<Map> allLimits = cacheService.getDataFromZsetByIndex(CacheConstants.CACHE_LIMITSTRATEGY_KEY, 0, -1);
        long max = System.currentTimeMillis();//以当前为基准
        //哪个每一个策略进行判断
        for (Map allLimit : allLimits) {
            Integer count = (Integer) allLimit.get("value");//限制的次数
            Double time = (Double) allLimit.get("score");//限制的时间,是一个秒
            //按照时间乘以1000 得到毫秒值,以当前时间为基准,向前推指定的时间数
            long min = (long) (max - time * 1000);//起始的时间
            //获取在这个时间范围内的已经发送的短信的次数
            Long currentCount = cacheService.getSizeByScore(CacheConstants.CACHE_PREFIX_SMS_LIMIT + clientID + destMobile, min, max);
            //和限制的此处进行比较
            if (currentCount >= count) {
                //超出了限制
                System.err.println("超出限制了");
                result = false;
                break;//跳出整个循环,因为已经属于限流了,其他的不需要判断了
            }
        }

        if (!result) {
            //被拦截了
//            //存在敏感词,应该通知客户,短信中包含敏感词,甚至可以把敏感词的内容返回
//            Standard_Report standard_report = new Standard_Report();
//            standard_report.setClientID(standard_submit.getClientID());
//            standard_report.setSrcID(standard_submit.getSrcSequenceId());
//            standard_report.setState(2);
//            standard_report.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_LIMIT_ERROR);
//            standard_report.setDescription("当前手机号发送短信太过频繁");
//            standard_report.setMobile(standard_submit.getDestMobile());
//            standard_report.setSendCount(1);//第几次推送, 是这样的,我们告诉客户结果,中间可能因为网络等原因没推送到,我们需要重试,但是呢又不是无限制重试,我们可以设置一个阈值
//            sendReportService.sendSmsReport(standard_report);
            standard_submit.setReportState(2);
            standard_submit.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_LIMIT_ERROR);
            standard_submit.setDescription("当前手机号"+standard_submit.getDestMobile()+"发送短信太过频繁");
        }else{
            System.err.println("没有超出限制");
            //todo 我们遇到的问题: 我们发现我们在这里直接保存次数到redis,在后面如果出现错误的情况下,短信明明发送失败,但是这边却认为发送成功,增加了一条记录
           // cacheService.add2Zset(CacheConstants.CACHE_PREFIX_SMS_LIMIT + clientID + destMobile, max + "", max);
        }

        return result;
    }
}
