package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TPhase;

import java.util.List;

public interface PhaseService {
    public int addPhase(TPhase tPhase);
    public int delPhase(Long id);
    public int updatePhase(TPhase tPhase);
    public TPhase findById(Long id);
    public List<TPhase> findALL();

    public DataGridResult findByPage(QueryDTO queryDTO);


    void syncPhase();
}
