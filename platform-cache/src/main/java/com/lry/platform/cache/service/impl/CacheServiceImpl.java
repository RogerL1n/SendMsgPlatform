package com.lry.platform.cache.service.impl;



import com.lry.platform.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by lry on 2022/7/12 16:59
 *
 * @author lry
 *
 */
@Service
public class CacheServiceImpl implements ICacheService {
    Set set = new HashSet<>();

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override

    public void save2Cache(String key, Object value, Integer expireSecond) {


        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || expireSecond == null || expireSecond <= 0) {

            return;
        }
        redisTemplate.opsForValue().set(key, value, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public void save2Cache(String key, Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {

            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            //传递了空的key
            return null;
        }

        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long mSet(Map map) {
        if (map == null || map.size() == 0) {
            return 0L;
        }
         redisTemplate.opsForValue().multiSet(map);
        return 1L;
    }

    @Override
    public Long del(String... keys) {
        if (keys == null) {
            return 0L;
        }
        return redisTemplate.delete(Arrays.asList(keys));
    }


    @Override
    public boolean expire(String key, Integer expireSeconds) {
        if (StringUtils.isEmpty(key) || expireSeconds == null || expireSeconds <= 0) {
            //代表数据不符合我们的要求
            return false;
        }
        return redisTemplate.expire(key,expireSeconds,TimeUnit.SECONDS);
    }

    @Override
    public long incr(String key, long delta) {
        //步长
        if (StringUtils.isEmpty(key) || delta == 0) {
            //这个返回值我们需要认真考虑下,因为你返回任何数据都有可能是正确情况下返回的数据
           //TODO 需要考虑返回值的类型
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Map<Object, Object> hGetAll(String key) {
        if (StringUtils.isEmpty(key)) {

            return null;
        }

        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void hSet(String key, String field, String value) {
        if (StringUtils.isEmpty(key)||StringUtils.isEmpty(field)) {
            //向hash中存放数据的时候这两个属性不能为空, 空则....
            return;
        }
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public void HMset(String key, Map map) {
       //我们不知道map是否允许长度为0,只知道不能为null,所以我们先假设允许为0,后面通过测试来进行判断
        if (StringUtils.isEmpty(key) || map == null) {
            return;
        }
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public Object hGet(String key, String field) {
        if (StringUtils.isEmpty(key)||StringUtils.isEmpty(field)) {

            return null;
        }

        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public Long hIncr(String key, String field, long delta) {
        if (StringUtils.isEmpty(key)||StringUtils.isEmpty(field)) {

            return null;
        }

        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    @Override
    public List<Object> pipelineOps(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        redisTemplate.opsForValue().multiSet(map); //TODO 更换存储方式的原因是为了方便查询,方式查询的时候各种错误,因为无法转成字符串返回
//        return  redisTemplate.executePipelined(new RedisCallback<Object>() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.openPipeline();//打开管道
//                map.forEach((key,value)->{
//                    //通过打开管道的连接执行相关的操作
//                    connection.set(key.getBytes(StandardCharsets.UTF_8), value.toString().getBytes(StandardCharsets.UTF_8));
//                });
//                return null;
//            }
//        });
        return new ArrayList<>();
    }

    @Override
    public Long rPush(String key, String... values) {
        if (StringUtils.isEmpty(key) || values == null || values.length == 0) {
            return 0L;
        }
       return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public List getDataFromList(String key, int start, int end) {
        if (StringUtils.isEmpty(key) || start < 0) {
            return new ArrayList<>();
        }
        List<Object> list = redisTemplate.opsForList().range(key, start, end);
        return list;

    }

    //BLACKFILTER     dirtyfilter limitfilter
    @Override
    public Long lremove(String key, String... values) {
        if (StringUtils.isEmpty(key) || values == null || values.length == 0) {
            return 0L;
        }
        Arrays.asList(values).forEach(value -> {
            redisTemplate.opsForList().remove(key, 0, value);
        });
        return 1L;
    }


    @Override
    public Long add2Set(String key, String... members) {
        if (StringUtils.isEmpty(key) || members == null || members.length == 0) {
            return 0L;
        }
        return redisTemplate.opsForSet().add(key, members);
    }

    @Override
    public Set<Object> members(String key) {
        if (StringUtils.isEmpty(key)) {
            return set;
        }
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Boolean isMember(String key, String member) {
        if (StringUtils.isEmpty(key)||StringUtils.isEmpty(member)) {
            return false;
        }
        return  redisTemplate.opsForSet().isMember(key,member);
    }

    @Override
    public Long deleteFromSet(String key, String... member) {
        if (StringUtils.isEmpty(key)||StringUtils.isEmpty(member)) {
            return 0L;
        }
        Long remove = redisTemplate.opsForSet().remove(key, member);
        return remove;
    }

    @Override
    public Boolean add2Zset(String key, String value, double score) {
        //先对数据进行判断
        if (StringUtils.isEmpty(key)||StringUtils.isEmpty(value)) {
            return false;
        }
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    @Override
    public Long getSizeByScore(String key, double min, double max) {
        if (StringUtils.isEmpty(key) || min > max) {
            return 0L;
        }
        long size = redisTemplate.opsForZSet().rangeByScore(key, min, max).size();
        return size;
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getDataFromZset(String key, long start, long end) {
        //如果start<0 查询不了完整的结果,所以没有意义
        if (StringUtils.isEmpty(key) || start < 0) {
            return null;
        }
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        return typedTuples;
    }

    @Override
    public String getByPiple(String key) {
//        List<Object> objectList = redisTemplate.executePipelined(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.openPipeline();
//                byte[] bytes = connection.get(key.getBytes());
//                return new String(bytes);
//            }
//        });
//        System.err.println(objectList);
        Object o = redisTemplate.boundValueOps(key).get();
        System.err.println(o);
        return "aaa";

    }
}
