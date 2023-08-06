package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TInstMapper;
import com.lry.platform.webmaster.dao.TPhaseMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.EventType;
import com.lry.platform.webmaster.events.UpdatePhaseEvent;
import com.lry.platform.webmaster.pojo.TInst;
import com.lry.platform.webmaster.pojo.TInstExample;
import com.lry.platform.webmaster.pojo.TPhase;
import com.lry.platform.webmaster.pojo.TPhaseExample;
import com.lry.platform.webmaster.service.PhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PhaseServiceImpl implements PhaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhaseServiceImpl.class);

    private TPhaseMapper tPhaseMapper;

    private TInstMapper tInstMapper;

    @Autowired
    public void settPhaseMapper(TPhaseMapper tPhaseMapper) {
        this.tPhaseMapper = tPhaseMapper;
    }
    @Autowired
    public void settInstMapper(TInstMapper tInstMapper) {
        this.tInstMapper = tInstMapper;
    }

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }




    @Override
    public int addPhase(TPhase tPhase) {
        int i = tPhaseMapper.insertSelective(tPhase);
        //异步更新缓存,防止因为失败导致事务失败
        context.publishEvent(UpdatePhaseEvent.createEvent(EventType.ADD, Arrays.asList(tPhase)));
        return i;
    }

    @Override
    public int delPhase(Long id) {
        TPhase tPhase = findById(id);
        int i = tPhaseMapper.deleteByPrimaryKey(id);
        //异步更新缓存,防止因为失败导致事务失败
        context.publishEvent(UpdatePhaseEvent.createEvent(EventType.DELETE, Arrays.asList(tPhase)));
        return i;
    }

    @Override
    public int updatePhase(TPhase tPhase) {
        //先查询原始的数据,从缓存中删除
        TPhase source = tPhaseMapper.selectByPrimaryKey(tPhase.getId());
        int i= tPhaseMapper.updateByPrimaryKey(tPhase);
        if(i>0){
            //异步更新缓存,防止因为失败导致事务失败
            context.publishEvent(UpdatePhaseEvent.createEvent(EventType.DELETE, Arrays.asList(source)));
            context.publishEvent(UpdatePhaseEvent.createEvent(EventType.UPDATE, Arrays.asList(tPhase)));
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TPhase findById(Long id) {
        return tPhaseMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TPhase> findALL() {
        return tPhaseMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TPhaseExample example = new TPhaseExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TPhase> messages = tPhaseMapper.selectByExample(example);
        for (TPhase message : messages) {
            Long provId = message.getProvId();
            TInst tInst1 = tInstMapper.selectByPrimaryKey(provId);
            message.setProvName(tInst1.getAreaname());
            Long cityId = message.getCityId();
            TInstExample tInstExample = new TInstExample();
            TInstExample.Criteria criteria = tInstExample.createCriteria();
            criteria.andIdEqualTo(cityId);
            criteria.andParentidEqualTo(provId);
            List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
            if(tInsts!=null&&tInsts.size()>0){
                TInst tInst = tInsts.get(0);
                message.setCityName(tInst.getAreaname());
            }
        }

        PageInfo<TPhase> info = new PageInfo<>(messages);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, messages);
        return result;
    }
@Override
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public void syncPhase() {
    LOGGER.error("同步手机号段数据开始");
    List<TPhase> allLists = findALL();
    //异步同步数据到 redis
    context.publishEvent(UpdatePhaseEvent.createEvent(EventType.SYNC, allLists));
    LOGGER.error("同步手机号段数据完成");
}
}
