package com.lry.platform.webmaster.dao;

import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TMenu;
import com.lry.platform.webmaster.pojo.TMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TMenuMapper {
    long countByExample(TMenuExample example);

    int deleteByExample(TMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMenu record);

    int insertSelective(TMenu record);

    List<TMenu> selectByExample(TMenuExample example);

    TMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMenu record, @Param("example") TMenuExample example);

    int updateByExample(@Param("record") TMenu record, @Param("example") TMenuExample example);

    int updateByPrimaryKeySelective(TMenu record);

    int updateByPrimaryKey(TMenu record);


    int deleteMenu(List<Long> ids);

    List<TMenu> findMenuByPage(QueryDTO query);

    List<TMenu> findMenu();

    List<String> findPermsByUserId(@Param("userId") Integer userId);

    List<Map<String, Object>> findDirMenuByUserId(@Param("userId") Integer userId);

    List<Map<String, Object>> findMenuNotButtonByUserId(@Param("userId") Integer userId, @Param("parentId") Integer parentId);

}
