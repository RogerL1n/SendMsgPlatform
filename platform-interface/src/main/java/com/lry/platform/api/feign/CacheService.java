package com.lry.platform.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "platform-cache", fallback = CacheServiceFallback.class)
public interface CacheService {
    @RequestMapping(value = "/cache/get/{key}", method = RequestMethod.GET)
    String get(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/getobject/{key}", method = RequestMethod.GET)
    Object getObject(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/delete", method = RequestMethod.POST)
    boolean del(@RequestParam("keys") String... keys);

    @RequestMapping(value = "/cache/hmset/{key}", method = RequestMethod.POST)
    void hmset(@PathVariable("key") String key, @RequestBody Map map);

    @RequestMapping(value = "/cache/hmget/{key}", method = RequestMethod.GET)
    public Map hmget(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/save2cachewithexpire", method = RequestMethod.POST)
    void set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expireSecond") Long expireSecond);

    @RequestMapping(value = "/cache/save2cache/{key}/{value}", method = RequestMethod.POST)
    void saveCache(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping(value = "/cache/incr", method = RequestMethod.GET)
    public Long incr(@RequestParam("key") String key, @RequestParam("delta") long value);

    @PostMapping("/cache/pipel")
    List<Object> pipelineOps(@RequestBody Map<String, Object> map);
}
