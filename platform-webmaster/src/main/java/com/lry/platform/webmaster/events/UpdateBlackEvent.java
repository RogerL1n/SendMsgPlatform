package com.lry.platform.webmaster.events;


   


import com.lry.platform.webmaster.pojo.TBlackList;

import java.util.List;

/**
 * Created by lry on 2021/8/1 11:35
 *
 * @author lry
 *   
 */
public class UpdateBlackEvent {

    private EventType eventType;//事件类型
    private List<TBlackList> tBlackList;//操作的黑名单

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public List<TBlackList> gettBlackList() {
        return tBlackList;
    }

    public void settBlackList(List<TBlackList> tBlackList) {
        this.tBlackList = tBlackList;
    }

    public UpdateBlackEvent() {
    }

    public UpdateBlackEvent(EventType eventType, List<TBlackList> tBlackList ) {
        this.eventType = eventType;
        this.tBlackList = tBlackList;
    }

    public static UpdateBlackEvent createEvent(EventType eventType, List<TBlackList> tBlackList) {
        UpdateBlackEvent updateBlackEvent = new UpdateBlackEvent(eventType, tBlackList);
        return updateBlackEvent;
    }
}
