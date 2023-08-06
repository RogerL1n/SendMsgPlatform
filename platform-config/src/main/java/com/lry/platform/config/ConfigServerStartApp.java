package com.lry.platform.config;


   


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by lry on 2022/7/12 15:52
 *
 * @author lry
 *   
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigServerStartApp {

    public static void main (String[] args){
        SpringApplication.run(ConfigServerStartApp.class,args);
    }
}
