package com.lry.platform.webmaster.service.impl;


//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.check.CheckType;
import com.lry.platform.webmaster.dao.TLimitMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.UpdateLimitEvent;
import com.lry.platform.webmaster.exceptions.AddDataErrorException;
import com.lry.platform.webmaster.exceptions.DeleteDataErrorException;
import com.lry.platform.webmaster.exceptions.QueryDataErrorException;
import com.lry.platform.webmaster.exceptions.UpdateDataErrorException;
import com.lry.platform.webmaster.pojo.TLimit;
import com.lry.platform.webmaster.pojo.TLimitExample;
import com.lry.platform.webmaster.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lry on 2021-02-28 09:54
 *
 * @Author lry
 */
@Service
@Transactional
public class LimitServiceImpl implements LimitService {

    private TLimitMapper limitMapper;

    @Autowired
    public void setLimitMapper(TLimitMapper limitMapper) {
        this.limitMapper = limitMapper;
    }

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }


    @Override
    @Transactional(noRollbackFor = UpdateDataErrorException.class)
    public int updateLimit(TLimit limit) {
        if (limit.isNull(CheckType.UPDATE)) {
            throw new UpdateDataErrorException("数据不完整", 500);
        }
        //现在的限流策略是所有的客户使用统一的策略,所以不能出现相同时间范围限制的策略,因此更新的时候不能有同一时间并且启用的策略
        if (limit.getLimitTime() > 0 && limit.getLimitState() == 1) {
            List<TLimit> limitList = findByStateAndTime((short) 1, limit.getLimitTime());//获取当前状态是启用并且时间和当前更新时间一样的数据
            if (limitList != null) {
                limitList.forEach(cLimit -> {
                    if (!cLimit.getId().equals(limit.getId())) {
                        //如果两个的id不一样,说明我们在设置一个相同时间的限流措施,会导致限流出现问题,不能更新
                        throw new UpdateDataErrorException("已经有一个id为" + cLimit.getId() + "的数据使用了此时间范围", 500);
                    }
                });
            }
        }
        int result = limitMapper.updateByPrimaryKeySelective(limit);

        //更新缓存数据
        context.publishEvent(UpdateLimitEvent.createEvent(findByState((short) 1)));

        return result;
    }

    @Override
    public int addLimit(TLimit limit) {
        if (limit.isNull(CheckType.ADD)) {
            throw new AddDataErrorException("数据不完整", 500);
        }
        //我们暂时只允许出现一次相同时间的限流机制,所以在添加的时候需要先看看有没有启用的和当前时间相同的限流措施
        if (limit.getLimitTime() > 0 && limit.getLimitState() == 1) {
            List<TLimit> limitList = findByStateAndTime((short) 1, limit.getLimitTime());//获取当前状态是启用并且时间和当前更新时间一样的数据
            if (limitList != null) {
                limitList.forEach(cLimit -> {
                    throw new UpdateDataErrorException("已经有一个id为" + cLimit.getId() + "的数据使用了此时间范围", 500);
                });

            }
        }
        int result = limitMapper.insert(limit);

        if (limit.getLimitState() == 1) {
            //如果是当前限流机制是启用,则更新缓存
            context.publishEvent(UpdateLimitEvent.createEvent(findByState((short) 1)));
        }
        return result;
    }

    @Override
    public int delLimits(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new DeleteDataErrorException("没有传递id", 500);
        }
        TLimitExample example = new TLimitExample();
        example.createCriteria().andIdIn(ids);
        int result = limitMapper.deleteByExample(example);
        context.publishEvent(UpdateLimitEvent.createEvent(findByState((short) 1)));
        return result;
    }

    @Override
    public TLimit findById(Integer id) {
        if (id == null || id <= 0) {
            throw new QueryDataErrorException("没有传递主键", 500);
        }
        return limitMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TLimit> findAll() {
        TLimitExample example = new TLimitExample();
        List<TLimit> tFilterList = limitMapper.selectByExample(example);
        return tFilterList;
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TLimitExample example = new TLimitExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TLimit> tFilterList = limitMapper.selectByExample(example);
        PageInfo<TLimit> info = new PageInfo<>(tFilterList);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tFilterList);
        return result;
    }

    /**
     * 根据状态来查看策略,按照当前的业务来说,如果是启用状态,应该最多只返回一个
     *
     * @param state
     * @return
     */
    @Override
    public List<TLimit> findByStateAndTime(Short state, Integer limitTime) {
        TLimitExample example = new TLimitExample();
        example.createCriteria().andLimitStateEqualTo(state).andLimitTimeEqualTo(limitTime);
        return limitMapper.selectByExample(example);
    }

    @Override
    public List<TLimit> findByState(Short state) {
        TLimitExample example = new TLimitExample();
        example.createCriteria().andLimitStateEqualTo(state);
        return limitMapper.selectByExample(example);
    }

    @Override
    public void syncLimits() {
        List<TLimit> filterList = findByState((short) 1);//获取启用的过滤器,理论上现在只有一个
        context.publishEvent(UpdateLimitEvent.createEvent(filterList));
    }

}
