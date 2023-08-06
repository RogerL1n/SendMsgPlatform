package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TAdminUserMapper;
import com.lry.platform.webmaster.dao.TRoleMapper;
import com.lry.platform.webmaster.dao.TUserRoleMapper;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAdminUser;
import com.lry.platform.webmaster.pojo.TRole;
import com.lry.platform.webmaster.pojo.TRoleExample;
import com.lry.platform.webmaster.pojo.TUserRoleKey;
import com.lry.platform.webmaster.service.AdminUserService;
import com.lry.platform.webmaster.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional()
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private TAdminUserMapper tAdminUserMapper;
    @Autowired
    private TRoleMapper tRoleMapper;
    @Autowired
    private TUserRoleMapper tUserRoleMapper;

    @Override
    public List<TAdminUser> findAll() {
        return tAdminUserMapper.selectByExample(null);
    }

    @Override
    public TAdminUser findByUsername(String username) {
        return tAdminUserMapper.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public DataGridResult findUserByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        if (queryDTO.getSort() != null && !queryDTO.getSort().equals("")) {
            queryDTO.setSort("id");
        }
        List<TAdminUser> byPage = tAdminUserMapper.findByPage(queryDTO);
        PageInfo<TAdminUser> info = new PageInfo<>(byPage);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, info.getList());
        return result;
    }

    @Override
    public int addAdminUser(TAdminUser tAdminUser) {
        String password = tAdminUser.getPassword();
        String usercode = tAdminUser.getUsercode();
        String s = MD5Utils.md5(password, usercode, 1024);
        tAdminUser.setPassword(s);
        int i =  tAdminUserMapper.insertSelective(tAdminUser);
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo("客户");
        List<TRole> tRoles = tRoleMapper.selectByExample(example);
        if(tRoles!=null&&tRoles.size()>0){
            TRole tRole = tRoles.get(0);
            TUserRoleKey record = new TUserRoleKey();
            record.setRoleId(tRole.getId());
            record.setUserId(tAdminUser.getId());
            tUserRoleMapper.insert(record);
        }
        return i;
    }

    @Override
    public int delAdminUser(Integer id) {
        return tAdminUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateAdminUser(TAdminUser tAdminUser) {
        return tAdminUserMapper.updateByPrimaryKey(tAdminUser);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TAdminUser findById(Integer id) {
        return tAdminUserMapper.selectByPrimaryKey(id);

    }

}
