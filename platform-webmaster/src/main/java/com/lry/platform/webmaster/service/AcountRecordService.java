package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAcountRecord;

import java.util.List;

public interface AcountRecordService {

    public int addAcount(TAcountRecord tAcountRecord);

    public int delAcount(Long id);

    public int updateAcount(TAcountRecord tAcountRecord);

    public TAcountRecord findById(Long id);

    public List<TAcountRecord> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    int updateByorderid(TAcountRecord record);
}
