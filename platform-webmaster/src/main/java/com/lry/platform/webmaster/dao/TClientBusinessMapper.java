package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TClientBusiness;
import com.lry.platform.webmaster.pojo.TClientBusinessExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TClientBusinessMapper {
    long countByExample(TClientBusinessExample example);

    int deleteByExample(TClientBusinessExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TClientBusiness record);

    int insertSelective(TClientBusiness record);

    List<TClientBusiness> selectByExample(TClientBusinessExample example);

    TClientBusiness selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TClientBusiness record, @Param("example") TClientBusinessExample example);

    int updateByExample(@Param("record") TClientBusiness record, @Param("example") TClientBusinessExample example);

    int updateByPrimaryKeySelective(TClientBusiness record);

    int updateByPrimaryKey(TClientBusiness record);

    int updateMoney(@Param("id") Long id,@Param("addmoney") Integer addmoney);
}
