package com.lry.platform.webmaster.service;
   


import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TNotify;

import java.util.List;

/**
 * Created by lry on 2021/8/3 17:47
 *
 * @author lry
 * @version 1.0
 * @since 1.0
 */

public interface NotifyService {


    int addNotify(TNotify tNotify);

    int delNotify(List<Integer> id);

    int updateNotify(TNotify tNotify);

    TNotify findById(Integer id);

    List<TNotify> findAll();

    DataGridResult findByPage(QueryDTO queryDTO);


}
