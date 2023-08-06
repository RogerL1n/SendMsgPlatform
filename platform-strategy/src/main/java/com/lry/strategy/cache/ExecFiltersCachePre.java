package com.lry.strategy.cache;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.strategy.events.FiltersChangeEvent;
import com.lry.strategy.feign.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lry on  2022/7/19 09:20
 * 要启用的过滤器的本地缓存,当前的方式是过滤器在缓存中是set格式存放,适用用不需要对过滤器进行排序
 * @author lry
 *   
 */
//@Component
public class ExecFiltersCachePre {

    private Set<String> filters = new HashSet<>();

    public Set<String> getFilters() {
        return filters;
    }


    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    //@PostConstruct
    public void init() {
        Set<String> members = cacheService.members(CacheConstants.CACHE_FILTER_KEY);
        if (members != null&&members.size()>0) {
            filters.clear();
            filters.addAll(members);
        }
    }

    @EventListener
    @Async
    public void onEvent(FiltersChangeEvent event) {
        init(); //更新缓存
    }
}
