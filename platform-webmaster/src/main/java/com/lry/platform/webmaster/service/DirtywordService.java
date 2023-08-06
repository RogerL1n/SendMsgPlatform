package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TDirtyword;

import java.util.List;

public interface DirtywordService {

    public int addDirtyword(TDirtyword tDirtyword);

    public int delDirtyword(Long id);

    public int updateDirtyword(TDirtyword tDirtyword);

    public TDirtyword findById(Long id);

    public List<TDirtyword> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    void syncDirtyword2Redis();
}
