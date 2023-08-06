package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TSearchParmas;

import java.util.List;

public interface SearchParmasService {

    int updateSearchParmas(TSearchParmas searchParmas);


    void addSearchParmas(TSearchParmas searchParmas);

    int deleteSearchParmas(List<Integer> ids);

    TSearchParmas findById(Integer id);

    List<TSearchParmas> findAll();

    DataGridResult findByPage(QueryDTO queryDTO);

    List<TSearchParmas> findByState(Short state);

    void syncSearchParmas();
}
