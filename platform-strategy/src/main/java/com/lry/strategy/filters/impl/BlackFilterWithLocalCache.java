package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.InterfaceExceptionDict;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.cache.BlackNumLocalCache;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.service.SendReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lry on  2022/7/15 11:38
 * 从本地缓存加载黑名单进行判断, 黑名单在程序启动的时候从 redis 中获取,本地缓存通过 mq 获取消息更新
 * @author lry
 *   
 */
@Component
public class BlackFilterWithLocalCache implements FilterChain {

    private SendReportService sendReportService;
    @Autowired
    public void setSendReportService(SendReportService sendReportService) {
        this.sendReportService = sendReportService;
    }
    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        //当前的过滤器进行的操作是判断手机号是不是黑名单
        //黑名单我们通过set的方式保存在了redis中
        //要判断的方式非常简单.直接判单当前的手机号在不在set中就可以
        String destMobile = standard_submit.getDestMobile();//获取到当前的手机号

        boolean isMember = BlackNumLocalCache.isMember(destMobile);//从本地中进行判断
        //如果返回true 意味着是黑名单,否则就不是
        if (isMember) {
            System.err.println(destMobile + "------->在黑名单中set");
            //应该拦截并通知客户有一个手机号在黑名单中
            //要通知客户,需要接口模块来做做事,所以我们现在只需要将结果通知到接口模块
//            Standard_Report standard_report = new Standard_Report();
//            standard_report.setClientID(standard_submit.getClientID());
//            standard_report.setSrcID(standard_submit.getSrcSequenceId());
//            standard_report.setState(2);
//            standard_report.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_BLACK_ERROR);
//            standard_report.setDescription(destMobile);
//            standard_report.setMobile(standard_submit.getDestMobile());
//            standard_report.setSendCount(1);//第几次推送, 是这样的,我们告诉客户结果,中间可能因为网络等原因没推送到,我们需要重试,但是呢又不是无限制重试,我们可以设置一个阈值
//            sendReportService.sendSmsReport(standard_report); //推送状态报告给客户
            //TODO 保存数据到我们自己的存储的地方,方便后续的查询,比如说保存到es中
            standard_submit.setReportState(2);
            standard_submit.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_BLACK_ERROR);
            standard_submit.setDescription(destMobile);
        }else{
            System.err.println(destMobile + "========>不不不不不不不在黑名单中set");
            return true;
        }
        return false;
    }
}
