package com.lry.strategy.service.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.platform.common.model.Standard_Report;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.service.SendReportService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lry on  2022/7/19 09:55
 *
 * @author lry
 *   
 */
@Service
public class SendReportServiceImpl implements SendReportService {

    private AmqpTemplate amqpTemplate;

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sendSmsReport(Standard_Report report) {
        //将数据发送到接口模块,通知用户结果

        Integer usertype = -1;

        try {
            String usertype1 = cacheService.hGet(CacheConstants.CACHE_PREFIX_CLIENT + report.getClientID(), "usertype");
            usertype = Integer.parseInt(usertype1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (2 == usertype) {
            amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PUSH_SMS_REPORT, report);
        }


    }

    @Override
    public void sendSmsToLog(Standard_Submit standard_submit) {
        // standard_submit.setReportState(2);
        // standard_submit.set
        amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_SMS_SEND_LOG, standard_submit);
    }
}
