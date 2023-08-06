package com.lry.platform.webmaster.pojo;

import com.lry.platform.webmaster.check.CheckNulll;
import com.lry.platform.webmaster.check.CheckType;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class TLimit implements Serializable, CheckNulll {
    private Integer id;

    private Integer limitTime;

    private Integer limitCount;

    private Short limitState;

    private String despcription;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Short getLimitState() {
        return limitState;
    }

    public void setLimitState(Short limitState) {
        this.limitState = limitState;
    }

    public String getDespcription() {
        return despcription;
    }

    public void setDespcription(String despcription) {
        this.despcription = despcription == null ? null : despcription.trim();
    }

    @Override
    public boolean isNull(CheckType type) {
        switch (type) {
            case ADD:

                return !(0 < limitTime && 0 < limitCount && 0 < limitState && !StringUtils.isEmpty(despcription));
            case UPDATE:
                return !(0 < id && (0 < limitTime || 0 < limitCount || 0 < limitState || !StringUtils.isEmpty(despcription)));
        }
        return CheckNulll.super.isNull(type);
    }
}
