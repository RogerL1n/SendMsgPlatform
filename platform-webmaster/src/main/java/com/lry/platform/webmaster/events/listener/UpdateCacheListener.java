package com.lry.platform.webmaster.events.listener;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.webmaster.cache.NotifyCache;
import com.lry.platform.webmaster.events.*;
import com.lry.platform.webmaster.mq.PushCacheUpdateService;
import com.lry.platform.webmaster.pojo.*;
import com.lry.platform.webmaster.service.api.CacheService;
import com.lry.platform.webmaster.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lry on 2021/8/1 11:35
 *
 * @author lry
 *   
 */

@Component
@EnableAsync
public class UpdateCacheListener {
    //获取是否发送更新数据消息的service,内部封装有通知更新的封装对象map
    private NotifyCache notifyCache;
    //缓存服务
    private CacheService cacheService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setNotifyCache(NotifyCache notifyCache) {
        this.notifyCache = notifyCache;
    }

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    //发送消息的服务 mq
    private PushCacheUpdateService pushCacheUpdateService;

    @Autowired
    public void setPushCacheUpdateService(PushCacheUpdateService pushCacheUpdateService) {
        this.pushCacheUpdateService = pushCacheUpdateService;
    }

    /**
     * 更新黑名单,主要是在增删改查的情况下更新redis并发送消息更新策略模块
     *
     * @param event
     */
    @EventListener
    @Async
    public void onBlackEvent(UpdateBlackEvent event) {
        System.err.println("发送更新黑名单缓存的事件" + Thread.currentThread().getName());
        switch (event.getEventType()) {
            //添加和更新缓存都是添加一条数据,更新会先执行删除操作
            case ADD:
                cacheService.add2Set(CacheConstants.CACHE_BLACK_KEY, Arrays.asList(event.gettBlackList().get(0).getMobile()));

                break;
            case UPDATE:
                //更新黑名单需要先删除数据,所以可以通过删除来触发消息,更新可以直接return
                cacheService.add2Set(CacheConstants.CACHE_BLACK_KEY, Arrays.asList(event.gettBlackList().get(0).getMobile()));
                return;
            case DELETE:
                cacheService.deleteFromSet(CacheConstants.CACHE_BLACK_KEY, event.gettBlackList().get(0).getMobile());
                break;
            case SYNC:
                cacheService.del(CacheConstants.CACHE_BLACK_KEY);
                List<String> numList = event.gettBlackList().stream().map(TBlackList::getMobile).collect(Collectors.toList());
                cacheService.add2Set(CacheConstants.CACHE_BLACK_KEY, numList);
                break;
        }
        pushMessage("blacklist", pushCacheUpdateService.updateBlackChannel());
    }


    /**
     * 更新敏感词,主要是在增删改查的情况下更新redis并发送消息更新策略模块
     *
     * @param event
     */
    @EventListener
    @Async
    public void onDirtyWordsEvent(UpdateDirtyWordsEvent event) {
        System.err.println("发送更新敏感词缓存的事件" + Thread.currentThread().getName());
        switch (event.getEventType()) {
            case ADD:
                cacheService.add2Set(CacheConstants.CACHE_DIRTY_KEY, Arrays.asList(event.getTDirtywordList().get(0).getDirtyword()));
            case UPDATE:
                //更新敏感词会删除数据,所以通过删除来发送消息即可,更新直接return
                cacheService.add2Set(CacheConstants.CACHE_DIRTY_KEY, Arrays.asList(event.getTDirtywordList().get(0).getDirtyword()));
                return;

            case DELETE:
                cacheService.deleteFromSet(CacheConstants.CACHE_DIRTY_KEY, event.getTDirtywordList().get(0).getDirtyword());
                break;

            case SYNC:
                cacheService.del(CacheConstants.CACHE_DIRTY_KEY);
                List<String> numList = event.getTDirtywordList().stream().map(TDirtyword::getDirtyword).collect(Collectors.toList());
                cacheService.add2Set(CacheConstants.CACHE_DIRTY_KEY, numList);
                break;
        }

            pushMessage("ditryword", pushCacheUpdateService.updateDirtyWordsChannel());

    }

    /**
     * 更新号段区域数据到redis
     *
     * @param event
     */
    @EventListener
    @Async
    public void onPhaseUpDate(UpdatePhaseEvent event) {
        List<TPhase> phaseList = event.getPhaseList();
        switch (event.getEventType()) {

            case ADD:
            case UPDATE:
                TPhase tPhase = phaseList.get(0);
                cacheService.saveCache("PHASE:" + tPhase.getPhase(), tPhase.getProvId() + "&" + tPhase.getCityId());
                break;
            case DELETE:
                cacheService.del("PHASE:" + phaseList.get(0).getPhase());
                break;
            case SYNC:
                List<TPhase> allLists = event.getPhaseList();
                //将数据转成 map 直接发送到缓存服务,批量添加到缓存
                Map<String, Object> map = allLists.stream().collect(HashMap::new, (m, currenttPhase) -> m.put(CacheConstants.CACHE_PREFIX_PHASE + currenttPhase.getPhase(), currenttPhase.getProvId().toString() + "&" + currenttPhase.getCityId().toString()), HashMap::putAll);
                cacheService.mSet(map);
                break;
        }
    }

    @EventListener
    @Async
    public void onClientBusinessUpdate(UpdateClientBusinessEvent event) {
        switch (event.getEventType()) {

            case ADD:
            case UPDATE:
                TClientBusiness clientBusiness = event.gettClientBusinessList().get(0);
                //转成 map 后放到 hash 中保存
                Map<String, String> map1 = JsonUtils.objectToMap(clientBusiness);
                cacheService.hmset("CLIENT:" + clientBusiness.getId(), map1);
                break;
            case DELETE:
                cacheService.del("CLIENT:" + event.gettClientBusinessList().get(0).getId());
                break;
            case SYNC:
                break;
        }
    }

    @Async
    @EventListener
    public void onEvent(UpdateFilterEvent event) {
        //先移除原有的过滤器
        cacheService.del(CacheConstants.CACHE_FILTER_KEY);
        //添加新的过滤器,向右添加来保证数据
        cacheService.rPush(CacheConstants.CACHE_FILTER_KEY, event.getFilters().split(","));
        //发送mq消息,通知策略模块更新策略
        pushMessage("filterlist", pushCacheUpdateService.updateFiltersChannel());

    }

    @Async
    @EventListener
    public void onEvent(UpdateLimitEvent event) {
        //先移除原有的过滤器
        cacheService.del(CacheConstants.CACHE_LIMITSTRATEGY_KEY);
        //添加新的过滤器,向右添加来保证数据
        event.getLimitList().forEach(limit -> {
            cacheService.add2Zset(CacheConstants.CACHE_LIMITSTRATEGY_KEY, limit.getLimitCount() + "", limit.getLimitTime());
        });

        //发送mq消息,通知策略模块更新策略
        pushMessage("limit", pushCacheUpdateService.updateLimitsChannel());

    }



    @Async
    @EventListener
    public void onEvent(SearchParmasEvent event) {
        //先移除原有的过滤器
        cacheService.del(CacheConstants.CACHE_SEARCHPARAM_KEY);
        //添加新的过滤器,向右添加来保证数据
        Map<String, TSearchParmas> parmasMap = event.getParmasList().stream().collect(Collectors.toMap(TSearchParmas::getName, TSearchParmas -> TSearchParmas));

        try {
            cacheService.saveCache(CacheConstants.CACHE_SEARCHPARAM_KEY, objectMapper.writeValueAsString(parmasMap));        //发送mq消息,通知策略模块更新策略
            pushMessage("searchparmas", pushCacheUpdateService.updateSearchParamsChannel());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    /**
     * 发送消息更新数据
     *
     * @param notifyTag
     * @param messageChannel
     */
    private void pushMessage(String notifyTag, MessageChannel messageChannel) {
        //获取当前操作是否通过mq刷新数据
        TNotify tNotify = notifyCache.getNotifyByTag(notifyTag);
        //如果当前操作有通知对象并且是启用了发送更新通知
        if (tNotify != null && 1 == tNotify.getNotifyState()) {
            System.err.println("发送" + notifyTag + "消息更新数据");
            //发送mq通知策略更新本地缓存数据
            messageChannel.send(new GenericMessage<>("0"));
            //发送消息,消息内容本身没有任何意义,主要的目的是
            // 让策略模块收到消息,知道发生黑名单变化了,然后更新缓存
        }
    }


}
