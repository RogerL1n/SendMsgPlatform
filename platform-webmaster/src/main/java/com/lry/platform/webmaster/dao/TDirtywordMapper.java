package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TDirtyword;
import com.lry.platform.webmaster.pojo.TDirtywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TDirtywordMapper {
    long countByExample(TDirtywordExample example);

    int deleteByExample(TDirtywordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TDirtyword record);

    int insertSelective(TDirtyword record);

    List<TDirtyword> selectByExample(TDirtywordExample example);

    TDirtyword selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TDirtyword record, @Param("example") TDirtywordExample example);

    int updateByExample(@Param("record") TDirtyword record, @Param("example") TDirtywordExample example);

    int updateByPrimaryKeySelective(TDirtyword record);

    int updateByPrimaryKey(TDirtyword record);
}
