package com.lry.platform.webmaster.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
//
//    /**
//     * 消费者数量，默认10
//     */
//    public static final int DEFAULT_CONCURRENT = 10;
//
//    /**
//     * 每个消费者获取最大投递数量 默认50
//     */
//    public static final int DEFAULT_PREFETCH_COUNT = 50;
//
//
//    /**
//     * 注入 Queue
//     * @return
//     */
//   // @Bean
//    public Queue Queue() {
//        return new Queue(RabbitMqConsants.TOPIC_PRE_SEND,true);
//    }
//
//
//    /**
//     * 并发消费配置
//     * @param configurer
//     * @param connectionFactory
//     * @return
//     */
//    //@Bean("pointTaskContainerFactory")
//    public SimpleRabbitListenerContainerFactory pointTaskContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setPrefetchCount(DEFAULT_PREFETCH_COUNT);
//        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
////        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }

@Bean
public Queue payresutQueue() {
    return new Queue("PAYRESULTQUEUE_WEBMASTER");
}

    @Bean
    public TopicExchange payresutExchange() {
        return new TopicExchange("pay_success_exchange");
    }

    @Bean
    public Binding bindingBuilder(Queue queue,TopicExchange topicExchange) {
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with("#.#");
        return binding;
    }
}

