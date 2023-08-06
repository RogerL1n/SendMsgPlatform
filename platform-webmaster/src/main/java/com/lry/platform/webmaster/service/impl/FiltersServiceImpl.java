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
import com.lry.platform.webmaster.dao.TFilterMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.UpdateFilterEvent;
import com.lry.platform.webmaster.exceptions.AddDataErrorException;
import com.lry.platform.webmaster.exceptions.DeleteDataErrorException;
import com.lry.platform.webmaster.exceptions.QueryDataErrorException;
import com.lry.platform.webmaster.exceptions.UpdateDataErrorException;
import com.lry.platform.webmaster.pojo.TFilter;
import com.lry.platform.webmaster.pojo.TFilterExample;
import com.lry.platform.webmaster.service.FiltersService;
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
public class FiltersServiceImpl implements FiltersService {
    private TFilterMapper filterMapper;

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void setFilterMapper(TFilterMapper filterMapper) {
        this.filterMapper = filterMapper;
    }

    @Override
    @Transactional(noRollbackFor = UpdateDataErrorException.class)
    public int updateFilters(TFilter filter) {
        if (filter.isNull(CheckType.UPDATE)) {
            throw new UpdateDataErrorException("数据不完整", 500);
        }
        //TODO 按照当前的规则,所有用户使用同一个策略,所以只可以有一个启用的策略,因此更新策略的时候需要看看是不是已经有启用的策略了或者当前在停用策略但是停用的是启用的策略
        TFilter source = findById(filter.getId());//获取当前策略的原始值

        if ((source.getFilterState() == 1 && filter.getFilterState() == 0)) {
            //如果当前过滤器分组之前是启用状态,当前要更新为停用,则不允许
            throw new UpdateDataErrorException("不可以禁用唯一启用的分组", 500);
        }
        List<TFilter> byState = findByState((short) 1);//获取更新前是启用状态的分组,用于后续更新用

        int update = filterMapper.updateByPrimaryKeySelective(filter);//更新当前过滤器
        if (update == 1 && filter.getFilterState() == 1) {
            //只有在更新的分组状态是要启用的时候才会发送事件
            context.publishEvent(UpdateFilterEvent.createEvent(filter.getFilters()));

            //如果当前已经有启用状态的过滤器
            if (byState != null && byState.size() > 0) {
                source = byState.get(0);
                if ((!source.getId().equals(filter.getId())) && 1 == filter.getFilterState()) {
                    //如果当前要更新的过滤器是要更新为启用并且当前的过滤器的id和数据库中已经启用的id不一样,则代表会变成两个启用的id,不通过
                    source.setFilterState((short) 0);
                    filterMapper.updateByPrimaryKeySelective(source);//将之前启用的过滤器分组自动禁用
                    throw new UpdateDataErrorException("因存在多个启用分组,自动禁用之前分组,id为"+source.getId(), 0);

                }
            }
        }

        return update;
    }

    @Override
    public void addFilters(TFilter filter) {
        if (filter.isNull(CheckType.ADD)) {
            throw new AddDataErrorException("数据不完整", 500);
        }
        //TODO 按照当前的规则,所有用户使用同一个策略,所以只可以有一个启用的策略,因此添加策略的时候需要看看是不是已经有启用的策略了

        List<TFilter> byState = findByState((short) 1);
        if ((byState != null && byState.size() > 0) && filter.getFilterState() == 1) {
            //代表当前已经有启用的过滤器,但是当前仍然要添加一个启用的过滤器分组
            throw new UpdateDataErrorException("只能有一个启用状态的分组", 500);
        }
        int result = filterMapper.insert(filter);

        if (result > 0 && filter.getFilterState() == 1) {
            //只有在添加的过滤器是启用状态的的时候才发送更新通知
            System.err.println("添加过滤器分组通知");
            context.publishEvent(UpdateFilterEvent.createEvent(filter.getFilters()));
        }

    }

    @Override
    public int deleteFilters(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new DeleteDataErrorException("没有传递id", 500);
        }
        //TODO 按照当前的规则,所有用户使用同一个策略,所以只可以有一个启用的策略,因此删除策略的时候需要看看是不是删除了启用的策略

        TFilterExample example = new TFilterExample();
        example.createCriteria().andIdIn(ids);
        //先查询出当前要删除的过滤器,进行状态判断
        List<TFilter> tFilters = filterMapper.selectByExample(example);
        if (tFilters != null && tFilters.size() > 0) {
            //要查看下当前删除的过滤器是不是启用状态
            tFilters.forEach(filter -> {
                if (filter.getFilterState() == 1) {
                    throw new DeleteDataErrorException("不可以删除启用状态的过滤器分组", 500);
                }
            });

            int result = filterMapper.deleteByExample(example);
            return result;
        }

        return 0;
    }

    @Override
    public TFilter findById(Integer id) {
        if (id == null || id <= 0) {
            throw new QueryDataErrorException("没有传递主键", 500);
        }
        return filterMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TFilter> findAll() {
        TFilterExample example = new TFilterExample();
        List<TFilter> tFilterList = filterMapper.selectByExample(example);
        return tFilterList;
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TFilterExample example = new TFilterExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TFilter> tFilterList = filterMapper.selectByExample(example);
        PageInfo<TFilter> info = new PageInfo<>(tFilterList);
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
    public List<TFilter> findByState(Short state) {
        TFilterExample example = new TFilterExample();
        example.createCriteria().andFilterStateEqualTo(state);
        return filterMapper.selectByExample(example);
    }

    @Override
    public void syncFilters() {
        List<TFilter> filterList = findByState((short) 1);//获取启用的过滤器,理论上现在只有一个
        if (filterList != null) {
            filterList.forEach(filter->{
                System.err.println("发送同步过滤器分组通知");
                context.publishEvent(UpdateFilterEvent.createEvent(filter.getFilters()));
            });
        }
    }

}
