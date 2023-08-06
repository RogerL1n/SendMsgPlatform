package com.lry.platform.webmaster.cache;

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


import com.lry.platform.webmaster.events.UpdateNotifyEvent;
import com.lry.platform.webmaster.pojo.TNotify;
import com.lry.platform.webmaster.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lry on 2021/8/8/0:51
 * @author lry
 * @since 1.0
 * @version 1.0
 */
@Component
public class NotifyCache {

    private Map<String, TNotify> notifyMap = new HashMap<>();//用于保存是否发起通知的map


    private NotifyService tNotifyService;
    @Autowired
    public void settNotifyService(NotifyService tNotifyService) {
        this.tNotifyService = tNotifyService;
    }

    @PostConstruct
    public void init() {
        System.err.println("初始化消息通知数据");
        List<TNotify> notifyList = tNotifyService.findAll();
        Map<String, TNotify> map = notifyList.stream().collect(Collectors.toMap(TNotify::getTag, TNotify -> TNotify));
        notifyMap.clear();
        notifyMap.putAll(map);
    }

    public TNotify getNotifyByTag(String tag) {
        return notifyMap.get(tag);
    }

    @Async
    @EventListener
    public void onEvent(UpdateNotifyEvent event) {
        init();//重新初始化数据
    }
}
