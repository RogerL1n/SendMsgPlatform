package com.lry.platform.webmaster.controller;

import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.platform.webmaster.dto.SmsDTO;
import com.lry.platform.webmaster.pojo.TAdminUser;
import com.lry.platform.webmaster.dto.R;
import com.lry.platform.webmaster.util.ShiroUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class SmsController {


    private AmqpTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @ResponseBody
    @RequestMapping("/sys/sms/save")
    public R addBlack(@RequestBody SmsDTO smsDTO){
        TAdminUser userEntity = ShiroUtils.getUserEntity();
        Integer clientid = userEntity.getClientid();
        String mobile = smsDTO.getMobile();
        String[] split = mobile.split("\n");
        for (String s : split) {
            Standard_Submit standard_submit = new Standard_Submit();
            standard_submit.setClientID(clientid);
            standard_submit.setDestMobile(s);
            standard_submit.setMessageContent(smsDTO.getContent());
            standard_submit.setSource(2);//因为是通过网页发送的,所以不需要接收状态报告,所以设置 2
            standard_submit.setSendTime(new Date());
            rabbitTemplate.convertAndSend(RabbitMqConsants.TOPIC_PRE_SEND, standard_submit);
        }
        return R.ok();
    }

}
