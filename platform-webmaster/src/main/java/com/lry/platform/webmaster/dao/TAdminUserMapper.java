package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAdminUser;
import com.lry.platform.webmaster.pojo.TAdminUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAdminUserMapper {
    long countByExample(TAdminUserExample example);

    int deleteByExample(TAdminUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAdminUser record);

    int insertSelective(TAdminUser record);

    List<TAdminUser> selectByExample(TAdminUserExample example);

    TAdminUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAdminUser record, @Param("example") TAdminUserExample example);

    int updateByExample(@Param("record") TAdminUser record, @Param("example") TAdminUserExample example);

    int updateByPrimaryKeySelective(TAdminUser record);

    int updateByPrimaryKey(TAdminUser record);

    List<TAdminUser> findByPage(QueryDTO queryDTO);

    public TAdminUser findByUsername(String username);
}
