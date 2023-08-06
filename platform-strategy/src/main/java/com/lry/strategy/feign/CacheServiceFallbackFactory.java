package com.lry.strategy.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 缓存服务的熔断
 */
@Component
public class CacheServiceFallbackFactory implements FallbackFactory<CacheService> {

    private CacheServiceFallback cacheServiceFallback = new CacheServiceFallback();
    @Override
    public CacheService create(Throwable throwable) {
        throwable.printStackTrace();
        return cacheServiceFallback;
    }
}
