package com.lry.platform.cache.controller;


   


import com.lry.platform.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lry on 2022/7/12 16:16
 *
 * @author lry
 *   
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    private ICacheService cacheService;

    @Autowired
    public void setCacheService(ICacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * 将数据保存到redis中的String类型
     *
     * @param key
     * @param value        value写object的原因是因为消费者可能会传递对象过来,我们转换为json保存
     * @param expireSecond 过期时间
     */
    @PostMapping("/save2cachewithexpire")
    public void save2Cache(String key, String value, Integer expireSecond) {

        cacheService.save2Cache(key, value, expireSecond);
    }

    /**
     * 永久不过期的数据
     *
     * @param key
     * @param value
     */
    @PostMapping("/save2cache/{key}/{value}")
    public void save2Cache(@PathVariable String key, @PathVariable String value) {
        cacheService.save2Cache(key, value);
    }

    /**
     * 批量添加数据到 string
     *
     * @param map
     * @return
     */
    @PostMapping("/mset")
    public Long mSet(@RequestBody Map map) {
        return cacheService.mSet(map);
    }

    /**
     * 获取指定key的值
     *
     * @param key
     * @return
     */
    @GetMapping("/getobject/{key}")
    public Object getobject(@PathVariable String key) {
        return cacheService.get(key);
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return cacheService.get(key);
    }

    /**
     * 删除指定的key
     *
     * @param keys 暂时写的是可变数组, 因为我们暂时还没有确定消费者传递的时候到底是传递集合方便还是可变数据方便
     * @return
     */
    @PostMapping("/delete")
    public Long del(String... keys) {
        return cacheService.del(keys);
    }

    /**
     * 给指定的key设置有效期
     *
     * @param key
     * @param expireSeconds
     * @return
     */
    @PostMapping("/expire/{key}/{expireSeconds}")
    public boolean expire(@PathVariable String key, @PathVariable Integer expireSeconds) {
        return cacheService.expire(key, expireSeconds);
    }

    /**
     * 自增
     *
     * @param key
     * @param delta 自增的布长, 写负数代表是自减
     * @return
     */
    @GetMapping("/incr")
    public long incr(String key, long delta) {
        return cacheService.incr(key, delta);
    }

    /**
     * 从redis中获取指定key 的hash数据
     *
     * @param key
     * @return
     */
    @GetMapping("/hmget/{key}")
    public Map<Object, Object> hGetAll(@PathVariable String key) {
        return cacheService.hGetAll(key);
    }

    /**
     * 向hash中添加一个属性数据
     *
     * @param key
     * @param field
     * @param value
     */
    @PostMapping("/hset")
    public void hSet(String key, String field, String value) {
        cacheService.hSet(key, field, value);
    }

    /**
     * 批量向redis指定key的hash中添加数据
     *
     * @param key
     * @param map
     */
    @PostMapping("/hmset/{key}")
    public void HMset(@PathVariable String key, @RequestBody Map map) {
        cacheService.HMset(key, map);
    }

    /**
     * 获取指定key的指定field的值
     *
     * @param key
     * @param field
     * @return
     */
    @GetMapping("/hget/{key}/{field}")
    public String hGet(@PathVariable String key, @PathVariable String field) {
        Object o = cacheService.hGet(key, field);
        return o == null ? null : o.toString();
    }

    @GetMapping("/hincr")
    public Long hIncr(String key, String field, long delta) {
        return cacheService.hIncr(key, field, delta);
    }


    @PostMapping("/pipel")
    public List<Object> pipelineOps(@RequestBody Map<String, Object> map) {
        return cacheService.pipelineOps(map);
    }

    @PostMapping("/add2set")
    public Long add2Set(String key, String... members) {
        return cacheService.add2Set(key, members);
    }

    /**
     * 从set中获取所有数据
     *
     * @param key
     * @return
     */
    @GetMapping("/members/{key}")
    public Set<Object> members(@PathVariable String key) {
        return cacheService.members(key);
    }

    /**
     * 判断某个数据在不在redis的set中
     *
     * @param key
     * @param member
     * @return
     */
    @GetMapping("/ismember/{key}/{member}")
    public Boolean isMember(@PathVariable String key, @PathVariable String member) {
        return cacheService.isMember(key, member);
    }

    @PostMapping("/zadd")
    Boolean add2Zset(String key, String value, double score) {
        return cacheService.add2Zset(key, value, score);
    }

    /**
     * 根据分数获取范围内的数据的个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    @GetMapping("/zrange")
    Long getSizeByScore(String key, double min, double max) {
        return cacheService.getSizeByScore(key, min, max);
    }

    @GetMapping("/rangewithscores")
    Set<ZSetOperations.TypedTuple<Object>> getDataFromZsetByIndex(String key, long start, long end) {
        return cacheService.getDataFromZset(key, start, end);
    }

    @GetMapping("/getpiple/{key}")
    String getByPiple(@PathVariable String key) {
        return cacheService.getByPiple(key);
    }

    @PostMapping("/rpush")
    Long rPush(String key, String... values) {
        return cacheService.rPush(key, values);
    }

    /**
     * 从list中获取指定位置的数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/lrange")
    List<String> getDataFromList(String key, int start, int end) {
        return cacheService.getDataFromList(key, start, end);
    }

    /**
     * 从list中一次性删除多个数据
     *
     * @param key
     * @param values
     * @return
     */
    @PostMapping("/lremove")
    Long lremove(String key, String... values) {
        return cacheService.lremove(key, values);
    }

    /**
     * 从set中一次性删除多个数据
     *
     * @param key
     * @param values
     * @return
     */
    @PostMapping("/removefromset")
    public Long deleteFromSet(String key, String... values) {
        return cacheService.deleteFromSet(key, values);
    }

}
