package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TDirtywordMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.EventType;
import com.lry.platform.webmaster.events.UpdateDirtyWordsEvent;
import com.lry.platform.webmaster.pojo.TDirtyword;
import com.lry.platform.webmaster.pojo.TDirtywordExample;
import com.lry.platform.webmaster.service.DirtywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class DirtywordServiceImpl implements DirtywordService {

    private TDirtywordMapper tDirtywordMapper;


    @Autowired
    public void settDirtywordMapper(TDirtywordMapper tDirtywordMapper) {
        this.tDirtywordMapper = tDirtywordMapper;
    }
    @Autowired


    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }


    @Override
    public int addDirtyword(TDirtyword tDirtyword) {
        int result = tDirtywordMapper.insertSelective(tDirtyword);//添加到数据库
        //添加到缓存,通过异步的方式,避免因更新缓存失败导致事物失败
        //通知策略同步缓存
        context.publishEvent(UpdateDirtyWordsEvent.createEvent(EventType.ADD,Arrays.asList(tDirtyword)));
        return result;
    }

    @Override
    public int delDirtyword(Long id) {
        TDirtyword tDirtyword = findById(id);
        int i = tDirtywordMapper.deleteByPrimaryKey(id);
        //同步到缓存,通过异步的方式,避免因更新缓存失败导致事物失败
        //通知策略同步缓存
        context.publishEvent(UpdateDirtyWordsEvent.createEvent(EventType.DELETE,Arrays.asList(tDirtyword)));
        return i;
    }

    @Override
    public int updateDirtyword(TDirtyword tDirtyword) {
        //先查询原始的敏感词,用于后面删除
        TDirtyword source = tDirtywordMapper.selectByPrimaryKey(tDirtyword.getId());
        if (source.getDirtyword().equals(tDirtyword.getDirtyword())) {
            //如果内容一样,不用更新
            return 0;
        }
        int i = tDirtywordMapper.updateByPrimaryKey(tDirtyword);
        if (i > 0) {
            //同步到缓存,通过异步的方式,避免因更新缓存失败导致事物失败
            //通知策略同步缓存
            context.publishEvent(UpdateDirtyWordsEvent.createEvent(EventType.UPDATE, Arrays.asList(tDirtyword)));
            context.publishEvent(UpdateDirtyWordsEvent.createEvent(EventType.DELETE,Arrays.asList(source)));
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TDirtyword findById(Long id) {
        return tDirtywordMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TDirtyword> findAll() {
        return tDirtywordMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TDirtywordExample example = new TDirtywordExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TDirtyword> tDirtywords = tDirtywordMapper.selectByExample(example);
        PageInfo<TDirtyword> info = new PageInfo<>(tDirtywords);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tDirtywords);
        return result;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public void syncDirtyword2Redis() {
        //获取所有的数据
        //通过通道操作保存到redis
        TDirtywordExample example = new TDirtywordExample();
        List<TDirtyword> tDirtywordList = tDirtywordMapper.selectByExample(example);//获取所有的数据
//        HashMap<Object, Object> hashMap = new HashMap<>();
//        for (TDirtyword tDirtyword : tDirtywordList) {
//            String dirtyword = tDirtyword.getDirtyword();
//            hashMap.put(CacheConstants.CACHE_PREFIX_DIRTYWORDS + dirtyword, "1");
//        }
        //以下代码使用的是 每个敏感词单独key的方式保存
//        Map<String, Object> map = tDirtywordList.stream().map(tDirtyword -> CacheConstants.CACHE_PREFIX_DIRTYWORDS + tDirtyword.getDirtyword())
//                .collect(Collectors.toMap(String -> String, String::length));
//        System.err.println(map);
//        cacheService.pipelineOps(map);

//        cacheService.del(CacheConstants.CACHE_DIRTY_KEY);
//        List<String> numList = tDirtywordList.stream().map(TDirtyword::getDirtyword).collect(Collectors.toList());
//        cacheService.add2Set(CacheConstants.CACHE_DIRTY_KEY, numList);
        //同步到缓存,通过异步的方式,避免因更新缓存失败导致事物失败
        //通知策略同步缓存
        context.publishEvent(UpdateDirtyWordsEvent.createEvent(EventType.SYNC,tDirtywordList));
    }


}
