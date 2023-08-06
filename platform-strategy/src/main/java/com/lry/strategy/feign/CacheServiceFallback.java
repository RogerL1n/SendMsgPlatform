package com.lry.strategy.feign;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存服务的熔断
 */
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
    public Long mSet(Map map) {
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
    public String hGet(String key, String field) {
        return null;
    }

    @Override
    public Long hIncr(String key, String field, long delta) {
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

    @Override
    public Long add2Set(String key, List<String> members) {
        return null;
    }

    @Override
    public Set<String> members(String key) {
        return null;
    }

    @Override
    public Boolean isMember(String key, String member) {
        return null;
    }

    @Override
    public Boolean add2Zset(String key, Object value, double score) {
        return null;
    }

    @Override
    public Long getSizeByScore(String key, double min, double max) {
        return 0L;
    }

    @Override
    public Set<Map> getDataFromZsetByIndex(String key, long start, long end) {
        return null;
    }

    @Override
    public Long rPush(String key, String... values) {
        return null;
    }

    @Override
    public List<String> getDataFromList(String key, int start, int end) {
        return null;
    }

    @Override
    public Long lremove(String key, String... values) {
        return null;
    }

    @Override
    public Long deleteFromSet(String key, String... values) {
        return null;
    }

}
