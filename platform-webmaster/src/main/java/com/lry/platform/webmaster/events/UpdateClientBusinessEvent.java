package com.lry.platform.webmaster.events;


   


import com.lry.platform.webmaster.pojo.TClientBusiness;

import java.util.List;

/**
 * Created by lry on 2021/8/1 16:18
 *
 * @author lry
 *   
 */

public class UpdateClientBusinessEvent {

    private EventType eventType;//事件类型
    private List<TClientBusiness> tClientBusinessList;//操作的黑名单

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public List<TClientBusiness> gettClientBusinessList() {
        return tClientBusinessList;
    }

    public void settClientBusinessList(List<TClientBusiness> tClientBusinessList) {
        this.tClientBusinessList = tClientBusinessList;
    }

    public UpdateClientBusinessEvent() {
    }

    public UpdateClientBusinessEvent(EventType eventType, List<TClientBusiness> tClientBusinessList ) {
        this.eventType = eventType;
        this.tClientBusinessList = tClientBusinessList;
    }

    public static UpdateClientBusinessEvent createEvent(EventType eventType, List<TClientBusiness> tClientBusinessList) {
        UpdateClientBusinessEvent updateClientBusinessEvent = new UpdateClientBusinessEvent(eventType, tClientBusinessList);
        return updateClientBusinessEvent;
    }
}
