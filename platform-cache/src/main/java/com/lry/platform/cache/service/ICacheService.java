package com.lry.platform.cache.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;



public interface ICacheService {
    /**
     * 将数据保存到redis中的String类型
     * @param key
     * @param value value写object的原因是因为消费者可能会传递对象过来,我们转换为json保存
     * @param expireSecond 过期时间
     */
    void save2Cache(String key, Object value, Integer expireSecond);

    /**
     * 永久不过期的数据
     * @param key
     * @param value
     */
    void save2Cache(String key, Object value);

    /**
     * 获取指定key的值
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 批量添加数据到 string
     * @param map
     * @return
     */
    Long mSet(Map map);

    /**
     * 删除指定的key
     * @param keys 暂时写的是可变数组, 因为我们暂时还没有确定消费者传递的时候到底是传递集合方便还是可变数据方便
     * @return
     */
    Long del(String... keys);

    /**
     * 给指定的key设置有效期
     * @param key
     * @param expireSeconds
     * @return
     */
    boolean expire(String key, Integer expireSeconds);

    /**
     * 自增
     * @param key
     * @param delta 自增的布长, 写负数代表是自减
     * @return
     */
    long incr(String key, long delta);

    /**
     * 从redis中获取指定key 的hash数据
     * @param key
     * @return
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 向hash中添加一个属性数据
     * @param key
     * @param field
     * @param value
     */
    void hSet(String key, String field, String value);

    /**
     * 批量向redis指定key的hash中添加数据
     * @param key
     * @param map
     */
    void HMset(String key, Map map);

    /**
     * 获取指定key的指定field的值
     * @param key
     * @param field
     * @return
     */
    Object hGet(String key, String field);

    /**
     * 从hash中进行自增或者自减
     * @param key
     * @param field
     * @param delta
     * @return
     */
    Long hIncr(String key, String field,long delta);
    /**
     * 通过管道进行操作
     * @param map
     * @return
     */
    List<Object> pipelineOps(Map<String, Object> map);

    /**
     * 向list中右侧添加数据,我们向右的原因是因为我们从左侧开始取数据,并且实现顺序
     * @param key
     * @param values
     * @return
     */
    Long rPush (String key, String... values);

    /**
     * 从list中获取指定位置的数据
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<String> getDataFromList(String key, int start, int end);

    /**
     * 从list中一次性删除多个数据
     * @param key
     * @param values
     * @return
     */
    Long lremove(String key, String... values);

    Long add2Set(String key, String... members);

    /**
     * 从set中获取所有数据
     * @param key
     * @return
     */
    Set<Object> members(String key);

    /**
     * 判断某个数据在不在redis的set中
     * @param key
     * @param member
     * @return
     */
    Boolean isMember(String key, String member);

    Long deleteFromSet(String key, String ... member);

    Boolean add2Zset(String key, String value, double score);

    /**
     * 根据分数获取范围内的数据的个数
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long getSizeByScore(String key, double min, double max);

    /**
     * 获取指定下标范围的数据
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<ZSetOperations.TypedTuple<Object>> getDataFromZset(String key, long start, long end);

    String getByPiple(String key);
}
