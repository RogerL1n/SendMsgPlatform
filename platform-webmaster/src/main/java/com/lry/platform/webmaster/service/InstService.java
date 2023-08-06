package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.pojo.TInst;

import java.util.List;

public interface InstService {
    //查询所有的省
    public List<TInst> findProvs();
    //查询省对应的市
    public List<TInst> findCitys(Long provId);

}
