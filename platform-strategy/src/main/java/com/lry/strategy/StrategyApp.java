package com.lry.strategy;


   


import com.lry.strategy.mq.inoutput.BlackNumUpdateInput;
import com.lry.strategy.mq.inoutput.DirtyWordsUpdateInput;
import com.lry.strategy.mq.inoutput.ExecFilterUpdateInput;
import com.lry.strategy.mq.inoutput.LimitUpdateInput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Created by lry on  2022/7/14 11:01
 *
 * @author lry
 *   
 */

@SpringBootApplication
@EnableFeignClients
@EnableBinding({BlackNumUpdateInput.class,
        LimitUpdateInput.class,
        DirtyWordsUpdateInput.class,
        ExecFilterUpdateInput.class})
public class StrategyApp {

    public static void main (String[] args){
        SpringApplication.run(StrategyApp.class,args);
    }
}
