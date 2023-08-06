package com.lry.platform.webmaster.mq;


   


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lry.platform.webmaster.pojo.TAcountRecord;
import com.lry.platform.webmaster.service.AcountRecordService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by lry on  2022/7/26 10:55
 *
 * @author lry
 *   
 */

@Component
public class PaymentResultListener {
    private ObjectMapper objectMapper;
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private AcountRecordService acountRecordService;

    @Autowired
    public void setAcountRecordService(AcountRecordService acountRecordService) {
        this.acountRecordService = acountRecordService;
    }

    @RabbitListener(queues = "PAYRESULTQUEUE_WEBMASTER")
    public void onMessage(String json) {
        System.err.println("webmaster收到的数据是:" + json);
        Map map = null;
        try {
            map = objectMapper.readValue(json, Map.class);
            TAcountRecord tAcountRecord = new TAcountRecord();
            tAcountRecord.setPaytime(new Date());
            tAcountRecord.setPaymentid((Integer) map.get("paymentid"));
            tAcountRecord.setPaymentorder((String) map.get("transaction_id"));//设置微信的订单号
            tAcountRecord.setPaymentinfo("success");
            tAcountRecord.setOrderid((String) map.get("out_trade_no"));//设置我们的订单号,我们需要根据这个订单号更新数据
            acountRecordService.updateByorderid(tAcountRecord);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }




    }
}
