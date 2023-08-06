package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TChannel;

import java.util.List;

public interface ChannelService {
    public int addChannel(TChannel tChannel);

    public int delChannel(Long id);

    public int updateChannel(TChannel tChannel);

    public TChannel findById(Long id);

    public List<TChannel> findALL();

    public DataGridResult findByPage(QueryDTO queryDTO);
}
