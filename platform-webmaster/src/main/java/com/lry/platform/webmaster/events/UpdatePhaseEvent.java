package com.lry.platform.webmaster.events;


   


import com.lry.platform.webmaster.pojo.TPhase;

import java.util.List;

/**
 * Created by lry on 2021/8/1 15:53
 *
 * @author lry
 *   
 */

public class UpdatePhaseEvent {
    private EventType eventType;

    private List<TPhase> phaseList;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public List<TPhase> getPhaseList() {
        return phaseList;
    }

    public void setPhaseList(List<TPhase> phaseList) {
        this.phaseList = phaseList;
    }

    public UpdatePhaseEvent() {
    }

    public UpdatePhaseEvent(EventType eventType, List<TPhase> phaseList) {

        this.eventType = eventType;
        this.phaseList = phaseList;
    }

    public static UpdatePhaseEvent createEvent(EventType eventType, List<TPhase> phaseList) {
        UpdatePhaseEvent updatePhaseEvent = new UpdatePhaseEvent(eventType, phaseList);
        return updatePhaseEvent;
    }
}
