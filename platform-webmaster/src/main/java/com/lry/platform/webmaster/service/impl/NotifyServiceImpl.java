package com.lry.platform.webmaster.service.impl;


   


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.check.CheckType;
import com.lry.platform.webmaster.dao.TNotifyMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.UpdateNotifyEvent;
import com.lry.platform.webmaster.exceptions.AddDataErrorException;
import com.lry.platform.webmaster.exceptions.DeleteDataErrorException;
import com.lry.platform.webmaster.exceptions.QueryDataErrorException;
import com.lry.platform.webmaster.exceptions.UpdateDataErrorException;
import com.lry.platform.webmaster.pojo.TNotify;
import com.lry.platform.webmaster.pojo.TNotifyExample;
import com.lry.platform.webmaster.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lry on 2021/8/7 14:40
 *
 * @author lry
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private ApplicationContext context;

    private TNotifyMapper notifyMapper;
    @Autowired
    public void setNotifyMapper(TNotifyMapper notifyMapper) {
        this.notifyMapper = notifyMapper;
    }

    @Override
    public int addNotify(TNotify tNotify) {
        if (tNotify.isNull(CheckType.ADD)) {
            throw new AddDataErrorException("数据不完整,请检查后再试", -1);
        }
        //TODO 更新本地通知缓存数据
        int result = notifyMapper.insert(tNotify);
        context.publishEvent(new UpdateNotifyEvent());
        return result;
    }

    @Override
    public int delNotify(List<Integer> ids) {
        if (ids == null || ids .size()<=0) {
            throw new DeleteDataErrorException("没有传递主键", -1);
        }
        //TODO 更新本地通知缓存数据
        TNotifyExample example = new TNotifyExample();
        example.createCriteria().andIdIn(ids);
        int result = notifyMapper.deleteByExample(example);
        context.publishEvent(new UpdateNotifyEvent());
        return result;
    }

    @Override
    public int updateNotify(TNotify tNotify) {
        if (tNotify.isNull(CheckType.UPDATE)) {
            throw new UpdateDataErrorException("数据不完整,请检查后再试", -1);
        }
        notifyMapper.updateByPrimaryKey(tNotify);
        //TODO 更新本地通知缓存数据
        context.publishEvent(new UpdateNotifyEvent());
        return 0;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TNotify findById(Integer id) {
        if (id == null || id <= 0) {
            throw new QueryDataErrorException("没有传递主键", -1);
        }
        return notifyMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TNotify> findAll() {
        return notifyMapper.selectByExample(new TNotifyExample());
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TNotifyExample example = new TNotifyExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TNotify> tNotifyList = notifyMapper.selectByExample(example);
        PageInfo<TNotify> info = new PageInfo<>(tNotifyList);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tNotifyList);
        return result;
    }
}
