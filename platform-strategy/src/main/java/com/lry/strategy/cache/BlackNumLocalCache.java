package com.lry.strategy.cache;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.strategy.events.BlackChangeEvent;
import com.lry.strategy.feign.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lry on  2022/7/15 11:45
 *
 * @author lry
 *   
 */
@Component
@EnableAsync
public class BlackNumLocalCache {
    //保存有黑名单的 set 集合
    private static Set<String> blackNums = new HashSet<>();

    private CacheService cacheService;
    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostConstruct
    //创建对象后执行
    public void initLocalCache() {
        //从redis中查询数据,保存到本地
        Set<String> blackNumsSet = cacheService.members(CacheConstants.CACHE_BLACK_KEY);//从缓存中获取到所有的黑名单
        if (blackNumsSet != null) {
            //更新本地缓存
            blackNums.clear();
            blackNums.addAll(blackNumsSet);
        }

    }


    public static boolean isMember(String member) {
        return blackNums.contains(member);//返回当前的set中是否包含这个数据,返回true代表包含,以为是在黑名单中
    }

    /**
     * 收到事件的时候重新从 redis 中获取数据
     * @param event
     */
    @EventListener
    @Async
    public void onEvent(BlackChangeEvent event) {
        initLocalCache(); //更新缓存
    }
}
