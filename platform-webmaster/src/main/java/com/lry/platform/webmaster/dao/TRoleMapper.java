package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.pojo.TRole;
import com.lry.platform.webmaster.pojo.TRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRoleMapper {
    long countByExample(TRoleExample example);

    int deleteByExample(TRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TRole record);

    int insertSelective(TRole record);

    List<TRole> selectByExample(TRoleExample example);

    TRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TRole record, @Param("example") TRoleExample example);

    int updateByExample(@Param("record") TRole record, @Param("example") TRoleExample example);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    List<String> findRolesByUserId(@Param("userId") Integer userId);

    void addRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    List<Integer> getRoleMenuIds(Integer roleId);
}
