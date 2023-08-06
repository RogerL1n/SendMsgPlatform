package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TNotify;
import com.lry.platform.webmaster.pojo.TNotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TNotifyMapper {
    long countByExample(TNotifyExample example);

    int deleteByExample(TNotifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TNotify record);

    int insertSelective(TNotify record);

    List<TNotify> selectByExample(TNotifyExample example);

    TNotify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TNotify record, @Param("example") TNotifyExample example);

    int updateByExample(@Param("record") TNotify record, @Param("example") TNotifyExample example);

    int updateByPrimaryKeySelective(TNotify record);

    int updateByPrimaryKey(TNotify record);
}
