package com.lry.platform.webmaster.pojo;

import com.lry.platform.webmaster.check.CheckNulll;
import com.lry.platform.webmaster.check.CheckType;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class TSearchParmas implements Serializable, CheckNulll {
    private Integer id;

    private String name;

    private String cloum;

    private String type;

    private Short tOrder;

    private Short state;

    private static final long serialVersionUID = 1L;


    @Override
    public boolean isNull(CheckType type) {
        switch (type) {

            case ADD:

                return StringUtils.isEmpty(name) || StringUtils.isEmpty(cloum) || StringUtils.isEmpty(type) || state == null;
            case UPDATE:
                return id == null || id <= 0;
        }
        return CheckNulll.super.isNull(type);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCloum() {
        return cloum;
    }

    public void setCloum(String cloum) {
        this.cloum = cloum == null ? null : cloum.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Short gettOrder() {
        return tOrder;
    }

    public void settOrder(Short tOrder) {
        this.tOrder = tOrder;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }
}
