package com.lry.platform.cache.config;


   


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by lry on 2022/7/12 16:06
 *
 * @author lry
 *  设置redistemplate参数中的object 的序列化方式
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);//设置链接工厂,主要是为了知道连接哪个redis

        StringRedisSerializer keySerializer = new StringRedisSerializer();//这是一个string类型的序列化方式
        redisTemplate.setKeySerializer(keySerializer); //设置key的序列化方式
        redisTemplate.setHashKeySerializer(keySerializer);//设置hashkey的的序列化方式,

        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        Jackson2JsonRedisSerializer<String> jsonStringRedisSerializer = new Jackson2JsonRedisSerializer<>(String.class);
        redisTemplate.setValueSerializer(jsonRedisSerializer);//我们的value到底如何序列化成内容,比如我们以json的方式,就需要一个json类型的序列化
        redisTemplate.setHashValueSerializer(jsonRedisSerializer); //设置hash的value的序列化方式
//        redisTemplate.setStringSerializer(jsonStringRedisSerializer);
        return redisTemplate;
    }
}
