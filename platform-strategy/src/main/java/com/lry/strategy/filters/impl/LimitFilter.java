package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.InterfaceExceptionDict;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.service.SendReportService;
import org.springframework.beans.factory.annotation.Autowired;

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
public class LimitFilter implements FilterChain {
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

        boolean result = false;
        //获取到当前客户针对这个号码已经发送了多少次短信,比如放到redis, 如果放到redis
        //然后进行比较
        int clientID = standard_submit.getClientID();
        String destMobile = standard_submit.getDestMobile();
        long max = System.currentTimeMillis();//以当前为基准
        long min = max - 60000;
        //获取一分钟内当前客户针对当前手机号发送的短信的数量
        Long count = cacheService.getSizeByScore(CacheConstants.CACHE_PREFIX_SMS_LIMIT + clientID + destMobile, min, max);
//        if (count >= 2) {
//            System.err.println("超过了一分钟的阈值限制");
//            //返回结果,推送状态报告
//            result= false;
//        } else {
//            System.err.println("没有超过一分钟的限制,所以应该继续判断一小时和一天的");
//            min = max - 1000 * 60 * 60;
//            count = cacheService.getSizeByScore(CacheConstants.CACHE_PREFIX_SMS_LIMIT + clientID + destMobile, min, max);
//            if (count >= 5) {
//                System.err.println("超过了一小时的阈值限制");
//                result= false;
//            } else {
//                min = max - 1000 * 60 * 60 * 24;
//                count = cacheService.getSizeByScore(CacheConstants.CACHE_PREFIX_SMS_LIMIT + clientID + destMobile, min, max);
//                if (count >= 10) {
//                    System.err.println("超过了一天的阈值限制");
//                    result= false;
//                } else {
//                    System.err.println("发送短信");
//                    cacheService.add2Zset(CacheConstants.CACHE_PREFIX_SMS_LIMIT + clientID + destMobile, max + "", max);
//                    result = true;
//                }
//            }
//        }


        if (!result) {
            //被拦截了
            //存在敏感词,应该通知客户,短信中包含敏感词,甚至可以把敏感词的内容返回
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
        }

        return result;
    }
}
