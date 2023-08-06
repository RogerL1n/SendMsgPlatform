package com.lry.platform.webmaster.pojo;

import com.lry.platform.webmaster.check.CheckNulll;
import com.lry.platform.webmaster.check.CheckType;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class TNotify implements Serializable, CheckNulll {
    private Integer id;

    private String tag;

    private String desp;

    private Short notifyState;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp == null ? null : desp.trim();
    }

    public Short getNotifyState() {
        return notifyState;
    }

    public void setNotifyState(Short notifyState) {
        this.notifyState = notifyState;
    }

    @Override
    public boolean isNull(CheckType type) {
        switch (type) {
            case ADD:
                return StringUtils.isEmpty(tag) || notifyState == null;
            case UPDATE:
                return StringUtils.isEmpty(id) || (StringUtils.isEmpty(tag) && StringUtils.isEmpty(desp) && notifyState == null);
            case DELETE:
                return StringUtils.isEmpty(id);
        }
        return CheckNulll.super.isNull(type);
    }
}
