package com.lry.platform.webmaster.service;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAdminUser;

import java.util.List;

public interface AdminUserService {
    public List<TAdminUser> findAll();

    public TAdminUser findByUsername(String username);

    public DataGridResult findUserByPage(QueryDTO queryDTO);

    public int addAdminUser(TAdminUser tAdminUser);

    public int delAdminUser(Integer id);

    public int updateAdminUser(TAdminUser tAdminUser);

    public TAdminUser findById(Integer id);

}
