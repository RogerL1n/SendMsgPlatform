package com.lry.platform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dao.TRoleMapper;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TRole;
import com.lry.platform.webmaster.pojo.TRoleExample;
import com.lry.platform.webmaster.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private TRoleMapper tRoleMapper;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<String> findRolsByUserID(Integer userId) {
        return tRoleMapper.findRolesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageInfo<TRole> getRoleList(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        List<TRole> roleList = getRoleList(new TRole());
        PageInfo<TRole> info = new PageInfo<TRole>(roleList);
        return info;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<TRole> getRoleList(TRole role) {
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        String name = role.getName();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameEqualTo(name);
        }
        Integer status = role.getStatus();
        if (!StringUtils.isEmpty(status)) {
            criteria.andStatusEqualTo(status);
        }
        List<TRole> tRoles = tRoleMapper.selectByExample(example);
        return tRoles;
    }

    @Override
    public void addRole(TRole role) {
        tRoleMapper.insertSelective(role);
    }

    @Override
    public void updateRole(TRole role) {
        tRoleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void deleteRole(int id) {
        tRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public TRole getRoleId(Integer id) {
        return tRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addRoleMenu(Integer roleId, Integer[] menuIds) {
        for (Integer menuId : menuIds) {
            tRoleMapper.addRoleMenu(roleId, menuId);
        }
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return tRoleMapper.getRoleMenuIds(roleId);
    }

}
