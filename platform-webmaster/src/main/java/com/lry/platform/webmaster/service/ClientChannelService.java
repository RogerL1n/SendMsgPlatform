package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TClientChannel;

import java.util.List;

public interface ClientChannelService {
    public int addClientChannel(TClientChannel tClientChannel);

    public int delClientChannel(Long id);

    public int updateClientChannel(TClientChannel tClientChannel);

    public TClientChannel findById(Long id);

    public List<TClientChannel> findAll();

    public DataGridResult findByPage(QueryDTO queryDTO);

    /**
     * 同步所有数据
     * @return
     */
    boolean syncAllDataToCache();
}
