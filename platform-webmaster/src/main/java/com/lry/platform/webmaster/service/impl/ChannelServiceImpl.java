package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TChannelMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TChannel;
import com.lry.platform.webmaster.pojo.TChannelExample;
import com.lry.platform.webmaster.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private TChannelMapper tChannelMapper;

    @Override
    public int addChannel(TChannel tChannel) {
        return tChannelMapper.insertSelective(tChannel);
    }

    @Override
    public int delChannel(Long id) {
        return tChannelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateChannel(TChannel tChannel) {
        return tChannelMapper.updateByPrimaryKey(tChannel);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TChannel findById(Long id) {
        return tChannelMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TChannel> findALL() {
        return tChannelMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TChannelExample example = new TChannelExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TChannel> tChannels = tChannelMapper.selectByExample(example);
        PageInfo<TChannel> info = new PageInfo<>(tChannels);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tChannels);
        return result;
    }

}
