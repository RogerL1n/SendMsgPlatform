package com.lry.platform.api.feign;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 缓存服务的熔断
 */
@Component
public class CacheServiceFallback implements CacheService {
    @Override
    public String get(String key) {
        return "";
    }

    @Override
    public Object getObject(String key) {
        return null;
    }

    @Override
    public boolean del(String... keys) {
        return false;
    }

    @Override
    public void hmset(String key, Map map) {
        return ;
    }

    @Override
    public Map hmget(String key) {
        return null;
    }

    @Override
    public void set(String key, String value, Long expireSecond) {

    }

    @Override
    public void saveCache(String key, String value) {

    }

    @Override
    public Long incr(String key, long value) {
        return null;
    }

    @Override
    public List<Object> pipelineOps(Map<String, Object> map) {
        return null;
    }

}
