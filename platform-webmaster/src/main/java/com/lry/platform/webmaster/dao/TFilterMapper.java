package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TFilter;
import com.lry.platform.webmaster.pojo.TFilterExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TFilterMapper {
    long countByExample(TFilterExample example);

    int deleteByExample(TFilterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFilter record);

    int insertSelective(TFilter record);

    List<TFilter> selectByExampleWithBLOBs(TFilterExample example);

    List<TFilter> selectByExample(TFilterExample example);

    TFilter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFilter record, @Param("example") TFilterExample example);

    int updateByExampleWithBLOBs(@Param("record") TFilter record, @Param("example") TFilterExample example);

    int updateByExample(@Param("record") TFilter record, @Param("example") TFilterExample example);

    int updateByPrimaryKeySelective(TFilter record);

    int updateByPrimaryKeyWithBLOBs(TFilter record);

    int updateByPrimaryKey(TFilter record);
}
