package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TMenu;
import com.lry.platform.webmaster.dto.R;

import java.util.List;

public interface MenuService {
    public DataGridResult findMenu(QueryDTO queryDTO);

    public R deleteMenu(List<Long> ids);

    public R selectMenu();

    public R saveMenu(TMenu tMenu);

    public R findMenuById(Integer menuId);

    public R updateMenu(TMenu tMenu);

    public List<String> findPermsByUserId(Integer userId);

    public R findUserMenu(Integer userId);

}
