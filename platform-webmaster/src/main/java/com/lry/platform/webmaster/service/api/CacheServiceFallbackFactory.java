package com.lry.platform.webmaster.service.api;


   


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lry on 2021/8/1 11:44
 *
 * @author lry
 *   
 */
@Component
public class CacheServiceFallbackFactory implements FallbackFactory<CacheService> {
    private CacheServiceFallback cacheServiceFallback = new CacheServiceFallback();
    @Override
    public CacheService create(Throwable throwable) {
        return cacheServiceFallback;
    }
}
