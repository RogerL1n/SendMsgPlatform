package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TBlackList;
import com.lry.platform.webmaster.pojo.TBlackListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBlackListMapper {
    long countByExample(TBlackListExample example);

    int deleteByExample(TBlackListExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TBlackList record);

    int insertSelective(TBlackList record);

    List<TBlackList> selectByExample(TBlackListExample example);

    TBlackList selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TBlackList record, @Param("example") TBlackListExample example);

    int updateByExample(@Param("record") TBlackList record, @Param("example") TBlackListExample example);

    int updateByPrimaryKeySelective(TBlackList record);

    int updateByPrimaryKey(TBlackList record);
}
