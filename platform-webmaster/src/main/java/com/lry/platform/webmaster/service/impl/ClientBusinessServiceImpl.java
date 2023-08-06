package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TClientBusinessMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.EventType;
import com.lry.platform.webmaster.events.UpdateClientBusinessEvent;
import com.lry.platform.webmaster.pojo.TClientBusiness;
import com.lry.platform.webmaster.pojo.TClientBusinessExample;
import com.lry.platform.webmaster.service.ClientBusinessService;
import com.lry.platform.webmaster.util.MD5Builder;
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
public class ClientBusinessServiceImpl implements ClientBusinessService {

    @Autowired
    private TClientBusinessMapper tClientBusinessMapper;



    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }



    @Override
    public int addClientBusiness(TClientBusiness tClientBusiness) {
        String pwd = tClientBusiness.getPwd();
        String build = MD5Builder.build(pwd, "UTF-8");
        String md5PASS = build.toUpperCase();
        tClientBusiness.setPwd(md5PASS);
        //持久层已经获得返回的主键
        int i = tClientBusinessMapper.insertSelective(tClientBusiness);
        //异步更新缓存,避免因为更新缓存失败导致事务失败
        context.publishEvent(UpdateClientBusinessEvent.createEvent(EventType.ADD,Arrays.asList(tClientBusiness) ));
        return i;
    }

    @Override
    public int delClientBusiness(Long id) {

        int i = tClientBusinessMapper.deleteByPrimaryKey(id);
        //异步更新缓存,避免因为更新缓存失败导致事务失败
        TClientBusiness business = new TClientBusiness();//创建一个对象,用于删除数据用
        business.setId(id);
        context.publishEvent(UpdateClientBusinessEvent.createEvent(EventType.DELETE,Arrays.asList(business) ));
        return i;
    }

    @Override
    public int updateClientBusiness(TClientBusiness tClientBusiness) {
        int i =  tClientBusinessMapper.updateByPrimaryKeySelective(tClientBusiness);
        if(i>0){
            TClientBusiness byId = findById(tClientBusiness.getId());
//            Map<String, String> map1 = JsonUtils.objectToMap(byId);
//            cacheService.hmset("CLIENT:"+tClientBusiness.getId(),map1);
            //异步更新缓存,避免因为更新缓存失败导致事务失败
            context.publishEvent(UpdateClientBusinessEvent.createEvent(EventType.ADD,Arrays.asList(byId)));
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TClientBusiness findById(Long id) {
        return tClientBusinessMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TClientBusiness> findAll() {
        return tClientBusinessMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TClientBusinessExample example = new TClientBusinessExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TClientBusiness> tClientBusinesses = tClientBusinessMapper.selectByExample(example);
        PageInfo<TClientBusiness> info = new PageInfo<>(tClientBusinesses);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tClientBusinesses);
        return result;
    }

    @Override
    public int updateMoney(Long id, Integer addmoney) {
        return tClientBusinessMapper.updateMoney(id,addmoney);
    }


}
