package com.lry.platform.webmaster.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(value = "platform-cache", fallbackFactory = CacheServiceFallbackFactory.class)
@RequestMapping("/cache")
public interface CacheService {
    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    String get(@PathVariable("key") String key);

    @RequestMapping(value = "/getobject/{key}", method = RequestMethod.GET)
    Object getObject(@PathVariable("key") String key);

    @PostMapping("/mset")
    Long mSet(@RequestBody Map map);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    boolean del(@RequestParam("keys") String... keys);

    @RequestMapping(value = "/hmset/{key}", method = RequestMethod.POST)
    void hmset(@PathVariable("key") String key, @RequestBody Map map);

    @RequestMapping(value = "/hmget/{key}", method = RequestMethod.GET)
    public Map hmget(@PathVariable("key") String key);

    @GetMapping("/hget/{key}/{field}")
    public String hGet(@PathVariable String key, @PathVariable String field);

    @GetMapping("/hincr")
    public Long hIncr(@RequestParam String key, @RequestParam String field, @RequestParam long delta);

    @RequestMapping(value = "/save2cachewithexpire", method = RequestMethod.POST)
    void set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expireSecond") Long expireSecond);

    @RequestMapping(value = "/save2cache/{key}/{value}", method = RequestMethod.POST)
    void saveCache(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping(value = "/incr", method = RequestMethod.GET)
    public Long incr(@RequestParam("key") String key, @RequestParam("delta") long value);

    @PostMapping("/pipel")
    List<Object> pipelineOps(@RequestBody Map<String, Object> map);


    @PostMapping("/add2set")
    public Long add2Set(@RequestParam("key") String key, @RequestParam("members") List<String> members);

    /**
     * 从set中获取所有数据
     *
     * @param key
     * @return
     */
    @GetMapping("/members/{key}")
    public Set<String> members(@PathVariable String key);

    /**
     * 判断某个数据在不在redis的set中
     *
     * @param key
     * @param member
     * @return
     */
    @GetMapping("/ismember/{key}/{member}")
    public Boolean isMember(@PathVariable String key, @PathVariable String member);


    @PostMapping("/zadd")
    Boolean add2Zset(@RequestParam String key, @RequestParam String value, @RequestParam double score);


    /**
     * 根据分数获取范围内的数据的个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    @GetMapping("/zrange")
    Long getSizeByScore(@RequestParam String key, @RequestParam double min, @RequestParam double max);

    @GetMapping("/rangewithscores")
    Set<Map> getDataFromZsetByIndex(@RequestParam String key, @RequestParam long start, @RequestParam long end);

    @PostMapping("/rpush")
    Long rPush(@RequestParam String key, @RequestParam String... values);

    /**
     * 从list中获取指定位置的数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/lrange")
    List<String> getDataFromList(@RequestParam String key, @RequestParam int start, @RequestParam int end);

    /**
     * 从list中一次性删除多个数据
     *
     * @param key
     * @param values
     * @return
     */
    @PostMapping("/lremove")
    Long lremove(@RequestParam String key, @RequestParam String... values);

    @PostMapping("/removefromset")
    Long deleteFromSet(@RequestParam String key,@RequestParam String... values);

}
