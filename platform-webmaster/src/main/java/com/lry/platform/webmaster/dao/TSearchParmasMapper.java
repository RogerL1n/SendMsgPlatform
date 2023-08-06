package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TSearchParmas;
import com.lry.platform.webmaster.pojo.TSearchParmasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSearchParmasMapper {
    long countByExample(TSearchParmasExample example);

    int deleteByExample(TSearchParmasExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSearchParmas record);

    int insertSelective(TSearchParmas record);

    List<TSearchParmas> selectByExample(TSearchParmasExample example);

    TSearchParmas selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSearchParmas record, @Param("example") TSearchParmasExample example);

    int updateByExample(@Param("record") TSearchParmas record, @Param("example") TSearchParmasExample example);

    int updateByPrimaryKeySelective(TSearchParmas record);

    int updateByPrimaryKey(TSearchParmas record);
}
