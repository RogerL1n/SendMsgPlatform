package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TPhase;
import com.lry.platform.webmaster.pojo.TPhaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPhaseMapper {
    long countByExample(TPhaseExample example);

    int deleteByExample(TPhaseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TPhase record);

    int insertSelective(TPhase record);

    List<TPhase> selectByExample(TPhaseExample example);

    TPhase selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TPhase record, @Param("example") TPhaseExample example);

    int updateByExample(@Param("record") TPhase record, @Param("example") TPhaseExample example);

    int updateByPrimaryKeySelective(TPhase record);

    int updateByPrimaryKey(TPhase record);
}
