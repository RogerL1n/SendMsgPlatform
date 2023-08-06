package com.lry.platform.webmaster.service;
   


import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TLimit;

import java.util.List;

/**
 * Created by lry on 2021/8/3 17:47
 *
 * @author lry
 * @version 1.0
 * @since 1.0
 */

public interface LimitService {


    int addLimit(TLimit tLimit);

    int delLimits(List<Integer> id);

    int updateLimit(TLimit tLimit);

    TLimit findById(Integer id);

    List<TLimit> findAll();

    DataGridResult findByPage(QueryDTO queryDTO);

    void syncLimits();

    List<TLimit> findByStateAndTime(Short state,Integer limitTime);
    List<TLimit> findByState(Short state);
}
