package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.webmaster.dao.TChannelMapper;
import com.lry.platform.webmaster.dao.TClientBusinessMapper;
import com.lry.platform.webmaster.dao.TClientChannelMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TChannel;
import com.lry.platform.webmaster.pojo.TClientBusiness;
import com.lry.platform.webmaster.pojo.TClientChannel;
import com.lry.platform.webmaster.pojo.TClientChannelExample;
import com.lry.platform.webmaster.service.ClientChannelService;
import com.lry.platform.webmaster.service.api.CacheService;
import com.lry.platform.webmaster.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClientChannelServiceImpl implements ClientChannelService {

    @Autowired
    private TClientChannelMapper tClientChannelMapper;
    @Autowired
    private TClientBusinessMapper tClientBusinessMapper;

    @Autowired
    private TChannelMapper tChannelMapper;

    @Autowired
    private CacheService cacheService;

    @Override
    public int addClientChannel(TClientChannel tClientChannel) {
        int i =  tClientChannelMapper.insertSelective(tClientChannel);
        Map<String, String> stringObjectMap = JsonUtils.objectToMap(tClientChannel);
        cacheService.hmset(CacheConstants.CACHE_PREFIX_ROUTER+tClientChannel.getClientid(),stringObjectMap);
        return i;
    }

    @Override
    public int delClientChannel(Long id) {
        TClientChannel tClientChannel = findById(id);
        cacheService.del(CacheConstants.CACHE_PREFIX_ROUTER+tClientChannel.getClientid());
        return tClientChannelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateClientChannel(TClientChannel tClientChannel) {
        int i = tClientChannelMapper.updateByPrimaryKey(tClientChannel);
        if (i > 0) {
            tClientChannel.setCorpname("");
            tClientChannel.setChannelname("");
            Map stringObjectMap = JsonUtils.objectToMap(tClientChannel);
            cacheService.hmset(CacheConstants.CACHE_PREFIX_ROUTER + tClientChannel.getClientid(), stringObjectMap);
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TClientChannel findById(Long id) {
        return tClientChannelMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TClientChannel> findAll() {
        return tClientChannelMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TClientChannelExample example = new TClientChannelExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TClientChannel> tClientChannels = tClientChannelMapper.selectByExample(example);
        for (TClientChannel tClientChannel : tClientChannels) {
            Long clientid = tClientChannel.getClientid();
            TClientBusiness tClientBusiness = tClientBusinessMapper.selectByPrimaryKey(clientid);
            String corpname = tClientBusiness.getCorpname();
            tClientChannel.setCorpname(corpname);
            long channelid = (long)tClientChannel.getChannelid();
            TChannel tChannel = tChannelMapper.selectByPrimaryKey(channelid);
            String channelname = tChannel.getChannelname();
            tClientChannel.setChannelname(channelname);
        }
        PageInfo<TClientChannel> info = new PageInfo<>(tClientChannels);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tClientChannels);
        return result;
    }

    @Override
    public boolean syncAllDataToCache() {
        List<TClientChannel> clientChannelList = findAll();
        for (TClientChannel tClientChannel : clientChannelList) {
            Map stringObjectMap = JsonUtils.objectToMap(tClientChannel);
            cacheService.hmset(CacheConstants.CACHE_PREFIX_ROUTER+tClientChannel.getClientid(),stringObjectMap);
        }
       return true;
    }
}
