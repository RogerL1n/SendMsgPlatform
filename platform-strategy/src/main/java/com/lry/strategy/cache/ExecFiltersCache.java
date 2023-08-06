package com.lry.strategy.cache;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.strategy.events.FiltersChangeEvent;
import com.lry.strategy.feign.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lry on  2022/7/19 09:20
 * 要启用的过滤器的本地缓存,当前过滤器在redis中是以list方式存放的,所以可以有顺序
 * @author lry
 *   
 */
@Component
public class ExecFiltersCache {

    private ArrayList<String> filters = new ArrayList<>();

    public ArrayList<String> getFilters() {
        return filters;
    }


    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostConstruct
    public void init() {
       // Set<String> members = cacheService.members(CacheConstants.CACHE_FILTER_KEY);
        List<String> filterLists = cacheService.getDataFromList(CacheConstants.CACHE_FILTER_KEY, 0, - 1);
        if (filterLists != null && filterLists.size() > 0) {
            filters.clear();
            filters.addAll(filterLists);
        }
    }

    @EventListener
    @Async
    public void onEvent(FiltersChangeEvent event) {
        init(); //更新缓存
    }
}
