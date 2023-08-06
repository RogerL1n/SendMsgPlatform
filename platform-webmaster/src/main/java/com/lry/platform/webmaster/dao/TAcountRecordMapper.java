package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TAcountRecord;
import com.lry.platform.webmaster.pojo.TAcountRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAcountRecordMapper {
    long countByExample(TAcountRecordExample example);

    int deleteByExample(TAcountRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TAcountRecord record);

    int insertSelective(TAcountRecord record);

    List<TAcountRecord> selectByExample(TAcountRecordExample example);

    TAcountRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TAcountRecord record, @Param("example") TAcountRecordExample example);

    int updateByExample(@Param("record") TAcountRecord record, @Param("example") TAcountRecordExample example);

    int updateByPrimaryKeySelective(TAcountRecord record);

    int updateByPrimaryKey(TAcountRecord record);

    int updateByorderid(TAcountRecord record);
}
