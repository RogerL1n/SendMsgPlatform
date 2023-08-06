package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TBlackListMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.EventType;
import com.lry.platform.webmaster.events.UpdateBlackEvent;
import com.lry.platform.webmaster.pojo.TBlackList;
import com.lry.platform.webmaster.pojo.TBlackListExample;
import com.lry.platform.webmaster.service.BlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional()
public class BlackServiceImpl implements BlackService {

    private TBlackListMapper tBlackListMapper;



    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void settBlackListMapper(TBlackListMapper tBlackListMapper) {
        this.tBlackListMapper = tBlackListMapper;
    }

    private ApplicationContext context;

    @Override
    public int addBlack(TBlackList tBlackList) {
        int i = tBlackListMapper.insertSelective(tBlackList);//添加到数据库
        //操作缓存不应当影响执行结果,所以通过异步执行
        // 通过事件当时发送更新缓存的消息
        context.publishEvent(UpdateBlackEvent.createEvent(EventType.ADD, Arrays.asList(tBlackList)));
        return i;
    }

    @Override
    public int delBlack(Long id) {
        TBlackList tBlackList = findById(id);
        int i = tBlackListMapper.deleteByPrimaryKey(id);//从数据库中删除数据
        //操作缓存不应当影响执行结果,所以通过异步执行
        // 通过事件当时发送更新缓存的消息
        context.publishEvent(UpdateBlackEvent.createEvent(EventType.DELETE, Arrays.asList(tBlackList)));
        return i;

    }

    @Override
    public int updateBlack(TBlackList tBlackList) {
        //TODO 更新应该先从缓存中删除原始数据,再添加更新后的数据,然后发送消息
        TBlackList source = tBlackListMapper.selectByPrimaryKey(tBlackList.getId());//先获取原始的数据,方便后面从 redis 中删除
        int i = tBlackListMapper.updateByPrimaryKey(tBlackList);
        if (i > 0) {
            //操作缓存不应当影响执行结果,所以通过异步执行
            // 通过事件当时发送更新缓存的消息
            context.publishEvent(UpdateBlackEvent.createEvent(EventType.DELETE, Arrays.asList(source)));
            context.publishEvent(UpdateBlackEvent.createEvent(EventType.UPDATE, Arrays.asList(tBlackList)));

        }
        return i;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TBlackList findById(Long id) {
        return tBlackListMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TBlackList> findAll() {
        return tBlackListMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TBlackListExample example = new TBlackListExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TBlackList> tBlackLists = tBlackListMapper.selectByExample(example);
        PageInfo<TBlackList> info = new PageInfo<>(tBlackLists);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tBlackLists);
        return result;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public void sync2RediseBlack() {
        TBlackListExample example = new TBlackListExample();
        List<TBlackList> tBlackListList = tBlackListMapper.selectByExample(example);//查询到所有的数据
        //TODO 下面注释的是之前每个黑名单一个key的方式,更改为 一个key对应所有黑名单的方式
//        Map<String, Object> map = tBlackListList.stream().map(tBlackList -> CacheConstants.CACHE_PREFIX_BLACK + tBlackList.getMobile())//
//                .collect(Collectors.toMap(String -> String, String::length));
//
//        cacheService.pipelineOps(map);

        //---------华丽的分割线----------
        //以set当时存取
        //因为同步是将所有数据保存到缓存中,所以为了防止数据错乱,可以先删除这个key,再添加数据
//        cacheService.del(CacheConstants.CACHE_BLACK_KEY);
//        List<String> numList = tBlackListList.stream().map(TBlackList::getMobile).collect(Collectors.toList());
//        cacheService.add2Set(CacheConstants.CACHE_BLACK_KEY, numList);
//        MessageChannel messageChannel = blackCacheChange.updateBlackChannel();
//        messageChannel.send(new GenericMessage<>("0"));//发送消息,消息内容本身没有任何意义,主要的目的是让策略模块收到消息,知道发生黑名单变化了,然后更新缓存

        //操作缓存不应当影响执行结果,所以通过异步执行
        // 通过事件当时发送更新缓存的消息
        context.publishEvent(UpdateBlackEvent.createEvent(EventType.SYNC, tBlackListList));
    }
}
