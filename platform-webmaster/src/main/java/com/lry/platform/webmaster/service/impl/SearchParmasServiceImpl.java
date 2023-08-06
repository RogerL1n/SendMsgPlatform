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
//


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.check.CheckType;
import com.lry.platform.webmaster.dao.TSearchParmasMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.events.SearchParmasEvent;
import com.lry.platform.webmaster.exceptions.DeleteDataErrorException;
import com.lry.platform.webmaster.exceptions.QueryDataErrorException;
import com.lry.platform.webmaster.exceptions.UpdateDataErrorException;
import com.lry.platform.webmaster.pojo.TSearchParmas;
import com.lry.platform.webmaster.pojo.TSearchParmasExample;
import com.lry.platform.webmaster.service.SearchParmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2021/8/9/18:51
 *
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class SearchParmasServiceImpl implements SearchParmasService {


    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private TSearchParmasMapper searchParmasMapper;

    @Autowired
    public void setSearchParmasMapper(TSearchParmasMapper searchParmasMapper) {
        this.searchParmasMapper = searchParmasMapper;
    }

    @Override
    public int updateSearchParmas(TSearchParmas searchParmas) {
        if (searchParmas.isNull(CheckType.UPDATE)) {
            throw new UpdateDataErrorException("没有传递主键", 500);
        }
        int update = searchParmasMapper.updateByPrimaryKeySelective(searchParmas);//更新数据
        //发送事件更新搜索参数缓存
        syncSearchParmas();
        return update;
    }

    @Override
    public void addSearchParmas(TSearchParmas searchParmas) {
        if (searchParmas.isNull(CheckType.ADD)) {
            throw new UpdateDataErrorException("数据不完整", 500);
        }
        int insert = searchParmasMapper.insert(searchParmas);
        //发送事件更新缓存
        if (1 == searchParmas.getState()) {
            syncSearchParmas();
        }

    }

    @Override
    public int deleteSearchParmas(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new DeleteDataErrorException("没有传递id", 500);
        }
        TSearchParmasExample example = new TSearchParmasExample();
        example.createCriteria().andIdIn(ids);
        int delete = searchParmasMapper.deleteByExample(example);
        //发送事件更新数据
        syncSearchParmas();
        return delete;
    }

    @Override
    public TSearchParmas findById(Integer id) {
        if (id == null || id <= 0) {
            throw new QueryDataErrorException("没有传递id", 500);
        }
        TSearchParmas searchParmas = searchParmasMapper.selectByPrimaryKey(id);
        return searchParmas;
    }

    @Override
    public List<TSearchParmas> findAll() {
        TSearchParmasExample example = new TSearchParmasExample();
        List<TSearchParmas> parmasList = searchParmasMapper.selectByExample(example);
        return parmasList;
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TSearchParmasExample example = new TSearchParmasExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TSearchParmas> tSearchParmasList = searchParmasMapper.selectByExample(example);
        PageInfo<TSearchParmas> info = new PageInfo<>(tSearchParmasList);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tSearchParmasList);
        return result;
    }

    @Override
    public List<TSearchParmas> findByState(Short state) {
        TSearchParmasExample example = new TSearchParmasExample();
        example.createCriteria().andStateEqualTo(state);
        List<TSearchParmas> tSearchParmasList = searchParmasMapper.selectByExample(example);
        return tSearchParmasList;
    }

    @Override
    public void syncSearchParmas() {
        List<TSearchParmas> parmasList = findByState((short) 1);//查询启用的参数
        context.publishEvent(SearchParmasEvent.createEvent(parmasList));
    }
}
