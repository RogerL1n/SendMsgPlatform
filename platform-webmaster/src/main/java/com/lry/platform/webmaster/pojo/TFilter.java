package com.lry.platform.webmaster.pojo;

import com.lry.platform.webmaster.check.CheckNulll;
import com.lry.platform.webmaster.check.CheckType;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class TFilter implements Serializable, CheckNulll {
    private Integer id;

    private Short filterState;

    private String filters;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getFilterState() {
        return filterState;
    }

    public void setFilterState(Short filterState) {
        this.filterState = filterState;
    }

    @Override
    public boolean isNull(CheckType type) {
        switch (type) {
            case ADD:
                return StringUtils.isEmpty(filters) || filterState == null || filterState < 0;
            case UPDATE:
                return (id==null||id<=0)||StringUtils.isEmpty(filters) || (filterState == null || filterState < 0);

        }
        return CheckNulll.super.isNull(type);
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters == null ? null : filters.trim();
    }
}
