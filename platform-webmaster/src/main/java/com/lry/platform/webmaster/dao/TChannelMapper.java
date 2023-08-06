package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TChannel;
import com.lry.platform.webmaster.pojo.TChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TChannelMapper {
    long countByExample(TChannelExample example);

    int deleteByExample(TChannelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TChannel record);

    int insertSelective(TChannel record);

    List<TChannel> selectByExample(TChannelExample example);

    TChannel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TChannel record, @Param("example") TChannelExample example);

    int updateByExample(@Param("record") TChannel record, @Param("example") TChannelExample example);

    int updateByPrimaryKeySelective(TChannel record);

    int updateByPrimaryKey(TChannel record);
}
