package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TClientBusiness;

import java.util.List;

public interface ClientBusinessService {
    public int addClientBusiness(TClientBusiness tClientBusiness);

    public int delClientBusiness(Long id);

    public int updateClientBusiness(TClientBusiness tClientBusiness);

    public TClientBusiness findById(Long id);

    public List<TClientBusiness> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);
    int updateMoney(Long id,Integer addmoney);
}
