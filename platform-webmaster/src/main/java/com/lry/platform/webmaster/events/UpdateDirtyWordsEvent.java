package com.lry.platform.webmaster.events;


   


import com.lry.platform.webmaster.pojo.TDirtyword;

import java.util.List;

/**
 * Created by lry on 2021/8/1 11:35
 *
 * @author lry
 *   
 */
public class UpdateDirtyWordsEvent {

    private EventType eventType;//事件类型
    private List<TDirtyword> TDirtywordList;//操作的敏感词

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public List<TDirtyword> getTDirtywordList() {
        return TDirtywordList;
    }

    public void setTDirtywordList(List<TDirtyword> TDirtywordList) {
        this.TDirtywordList = TDirtywordList;
    }

    public UpdateDirtyWordsEvent() {
    }

    public UpdateDirtyWordsEvent(EventType eventType, List<TDirtyword> TDirtywordList ) {
        this.eventType = eventType;
        this.TDirtywordList = TDirtywordList;
    }

    public static UpdateDirtyWordsEvent createEvent(EventType eventType, List<TDirtyword> TDirtywordList) {
        UpdateDirtyWordsEvent updateDirtyWordsEvent = new UpdateDirtyWordsEvent(eventType, TDirtywordList);
        return updateDirtyWordsEvent;
    }
}
