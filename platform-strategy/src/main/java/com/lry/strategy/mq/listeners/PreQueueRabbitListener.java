package com.lry.strategy.mq.listeners;


   


import com.lry.platform.common.constants.RabbitMqConsants;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.service.DataFilterManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lry on  2022/7/14 11:02
 *
 * @author lry
 *   
 */

@Component
public class PreQueueRabbitListener {

    private DataFilterManager dataFilterManager;

    @Autowired
    //@Qualifier("dataFilterManagerImpl2") 这个注解的目是将指定id 的对象注入进来,主要用在这个类型的对象有多个时候
    public void setDataFilterManager(DataFilterManager dataFilterManager) {
        this.dataFilterManager = dataFilterManager;
    }

    /**
     * 收到发送短信的mq后处理消息, 通过具体的实现类来处理消息
     * @param standard_submit
     */
    @RabbitListener(queues = RabbitMqConsants.TOPIC_PRE_SEND)
    public void onMessage(Standard_Submit standard_submit) {
       try {
           System.err.println(standard_submit);
           long currentTimeMillis = System.currentTimeMillis();
           System.err.println(currentTimeMillis);
           //处理消息, 怎么处理? 通过每个策略进行处理,有多少个策略? 不知道,很多个
           dataFilterManager.dealSms(standard_submit);
           System.err.println(System.currentTimeMillis()-currentTimeMillis);
       }catch (Exception e){
          e.printStackTrace();
          //todo 处理异常即可
       }
    }
}
