package com.lry.strategy.cache;

//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//


import com.lry.platform.common.constants.CacheConstants;
import com.lry.strategy.events.LimitChangeEvent;
import com.lry.strategy.feign.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2021/8/8/19:30
 *
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
@Component
public class LimitLocalCache {

    private Set<Map> allLimits = new HashSet<>();


    public Set<Map> getAllLimits() {
        return allLimits;
    }

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostConstruct
    public void init() {
        Set<Map> allLimitsFromRedis = cacheService.getDataFromZsetByIndex(CacheConstants.CACHE_LIMITSTRATEGY_KEY, 0, -1);
        if (allLimitsFromRedis != null) {
            allLimits.clear();
            allLimits.addAll(allLimitsFromRedis);
        }
    }

    /**
     * 在收到限流发生变化的消息时候更新
     * @param event
     */
    @EventListener
    @Async
    public void onEvent(LimitChangeEvent event) {
        init();
    }
}
