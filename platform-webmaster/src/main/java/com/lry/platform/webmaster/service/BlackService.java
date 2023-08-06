package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TBlackList;

import java.util.List;

public interface BlackService {
    public int addBlack(TBlackList tBlackList);

    public int delBlack(Long id);

    public int updateBlack(TBlackList tBlackList);

    public TBlackList findById(Long id);

    public List<TBlackList> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    void sync2RediseBlack();
}
