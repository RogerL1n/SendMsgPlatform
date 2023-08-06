package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TAcountRecordMapper;
import com.lry.platform.webmaster.dao.TClientBusinessMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAcountRecord;
import com.lry.platform.webmaster.pojo.TAcountRecordExample;
import com.lry.platform.webmaster.pojo.TClientBusiness;
import com.lry.platform.webmaster.service.AcountRecordService;
import com.lry.platform.webmaster.service.api.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AcountRecordImpl implements AcountRecordService {

    @Autowired
    private TAcountRecordMapper tAcountRecordMapper;

    @Autowired
    private TClientBusinessMapper tClientBusinessMapper;

    @Autowired
    private CacheService cacheService;


    //初始化费用数据到缓存
    @Override
    public int addAcount(TAcountRecord tAcountRecord) {
        Integer paidvalue = tAcountRecord.getPaidvalue()*1000;//转为厘
        tAcountRecord.setPaidvalue(paidvalue);
        tAcountRecord.setCreatetime(new Date());
        int i = tAcountRecordMapper.insertSelective(tAcountRecord);
        Integer paidValueStr  = (Integer)cacheService.getObject("CUSTOMER_FEE:" + tAcountRecord.getClientid());
//        if(!StringUtils.isEmpty(paidValueStr)){
//          //  paidvalue += paidValueStr;
//        }
        cacheService.incr("CUSTOMER_FEE:"+tAcountRecord.getClientid(),paidvalue);
        return i;
    }

    @Override
    public int delAcount(Long id) {
        return tAcountRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateAcount(TAcountRecord tAcountRecord) {
        return tAcountRecordMapper.updateByPrimaryKey(tAcountRecord);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TAcountRecord findById(Long id) {
        return tAcountRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TAcountRecord> findAll() {
        return tAcountRecordMapper.selectByExample(null);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(),queryDTO.getLimit());
        TAcountRecordExample example = new TAcountRecordExample();
        String sort = queryDTO.getSort();
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause("id");
        }
        List<TAcountRecord> tAcountRecords = tAcountRecordMapper.selectByExample(example);
        for (TAcountRecord tAcountRecord : tAcountRecords) {
            Long clientid = tAcountRecord.getClientid();
            TClientBusiness tClientBusiness = tClientBusinessMapper.selectByPrimaryKey(clientid);
            String corpname = tClientBusiness.getCorpname();
            //tAcountRecord.setCorpname(corpname);
            Integer paidvalue = tAcountRecord.getPaidvalue();
            tAcountRecord.setPaidvalue(paidvalue/1000);
        }
        PageInfo<TAcountRecord> info = new PageInfo<>(tAcountRecords);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total,tAcountRecords);
        return result;
    }

    @Override
    public int updateByorderid(TAcountRecord record) {
         tAcountRecordMapper.updateByorderid(record);
        TAcountRecordExample tAcountRecordExample = new TAcountRecordExample();
        TAcountRecordExample.Criteria criteria = tAcountRecordExample.createCriteria();
        criteria.andOrderidEqualTo(record.getOrderid());//设置查询条件, orderid 等于什么值
        List<TAcountRecord> acountRecords = tAcountRecordMapper.selectByExample(tAcountRecordExample);
        if (acountRecords != null && acountRecords.size() ==1) {
            TAcountRecord tAcountRecord = acountRecords.get(0);
            Integer paidvalue = tAcountRecord.getPaidvalue();//当前充值的金额
            Long clientid = tAcountRecord.getClientid();//哪个客户充值的
            tClientBusinessMapper.updateMoney(clientid, paidvalue);
        }

        return 1;
    }


}
