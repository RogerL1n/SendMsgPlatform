package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TLimit;
import com.lry.platform.webmaster.pojo.TLimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TLimitMapper {
    long countByExample(TLimitExample example);

    int deleteByExample(TLimitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TLimit record);

    int insertSelective(TLimit record);

    List<TLimit> selectByExample(TLimitExample example);

    TLimit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TLimit record, @Param("example") TLimitExample example);

    int updateByExample(@Param("record") TLimit record, @Param("example") TLimitExample example);

    int updateByPrimaryKeySelective(TLimit record);

    int updateByPrimaryKey(TLimit record);
}
