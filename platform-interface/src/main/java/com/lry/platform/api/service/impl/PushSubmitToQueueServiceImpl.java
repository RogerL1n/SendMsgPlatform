package com.lry.platform.api.service.impl;





import com.lry.platform.api.service.PushSubmitToQueueService;
import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.platform.common.model.Standard_Submit;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lry on  2022/7/14 10:03
 *
 * @author lry
 *
 */
@Service
public class PushSubmitToQueueServiceImpl implements PushSubmitToQueueService {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * 发送数据到mq
     * @param standard_submitList
     */
    @Override
    public void sendSubmitToQueue(List<Standard_Submit> standard_submitList) {
        //每一个手机号对应的短信要一条一条发送,而不是作为一个整体发送短信, 所以此处进行了分拆,拆分后挨个发送
        standard_submitList.forEach(submit->{
            amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PRE_SEND,submit);
        });
    }
}
