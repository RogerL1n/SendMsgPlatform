package com.lry.platform.webmaster;

import com.lry.platform.webmaster.mq.PushCacheUpdateService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.lry.platform.webmaster.dao")//Mybatis的DAO所在包
@ServletComponentScan(basePackages = "com.qianfeng.platform.webmaster.config")
@EnableFeignClients
@EnableBinding({PushCacheUpdateService.class})
public class SmsPlatformWebManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(platformWebManagerApplication.class, args);
    }

//    @Autowired
//    private DataSource dataSource;
//
//    private String transactionExecution = "execution(* com.qianfeng.platform.webmaster.service.impl..*(..))";
//
//    //事务传播机制
//    @Bean
//    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(transactionExecution);
//        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
//        advisor.setPointcut(pointcut);
//        Properties attributes = new Properties();
//        attributes.setProperty("find*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT,readOnly");
//        attributes.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
//        attributes.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
//        attributes.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
//        attributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
//        TransactionInterceptor txAdvice = new TransactionInterceptor(new DataSourceTransactionManager(dataSource), attributes);
//        advisor.setAdvice(txAdvice);
//        return advisor;
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
