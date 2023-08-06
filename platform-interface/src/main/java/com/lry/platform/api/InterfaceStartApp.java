package com.lry.platform.api;





import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lry on  2022/7/13 11:44
 *
 * @author lry
 *
 */
@SpringBootApplication
@EnableFeignClients
public class InterfaceStartApp {
    public static void main(String[] args) {
        SpringApplication.run(InterfaceStartApp.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
