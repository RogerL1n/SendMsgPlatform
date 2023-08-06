package com.lry.platform.eureka;


   


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by lry on 2022/7/12 15:40
 *
 * @author lry
 *   
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaStartApp {
    public static void main (String[] args){
        SpringApplication.run(EurekaStartApp.class,args);
    }
}
