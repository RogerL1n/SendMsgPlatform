package com.lry.platform.api.mq;





import com.lry.platform.api.service.SendReportService;
import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.platform.common.model.Standard_Report;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lry on  2022/7/19 10:11
 *
 * @author lry
 *
 */
@Component
public class ReceiveReportListener {

    private SendReportService sendReportService;

    @Autowired
    public void setSendReportService(SendReportService sendReportService) {
        this.sendReportService = sendReportService;
    }

    @RabbitListener(queues = {RabbitMqConsants.TOPIC_PUSH_SMS_REPORT})
    public void onMessage(Standard_Report report) {
        System.err.println("收到应该发送给客户的状态报告数据:"+report);
        //将结果发送给客户
        sendReportService.sendReport(report);
    }
}
