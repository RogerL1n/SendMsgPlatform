package com.lry.platform.webmaster.service.impl;

import com.lry.platform.webmaster.dao.TInstMapper;
import com.lry.platform.webmaster.pojo.TInst;
import com.lry.platform.webmaster.pojo.TInstExample;
import com.lry.platform.webmaster.service.InstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstServiceImpl implements InstService {

    @Autowired
    private TInstMapper tInstMapper;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TInst> findProvs() {
        TInstExample tInstExample = new TInstExample();
        TInstExample.Criteria criteria = tInstExample.createCriteria();
        criteria.andParentidEqualTo(1L);
        List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
        return tInsts;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TInst> findCitys(Long provId) {
        TInstExample tInstExample = new TInstExample();
        TInstExample.Criteria criteria = tInstExample.createCriteria();
        criteria.andParentidEqualTo(provId);
        List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
        return tInsts;
    }
}
