package com.lry.platform.webmaster.controller;

import com.github.pagehelper.PageInfo;
import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TRole;
import com.lry.platform.webmaster.service.MenuService;
import com.lry.platform.webmaster.service.RoleService;
import com.lry.platform.webmaster.dto.AjaxMessage;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/system/role/list")
    public R table(QueryDTO queryDTO) {
        PageInfo<TRole> pageInfo = roleService.getRoleList(queryDTO);
        long total = pageInfo.getTotal();
        DataGridResult result = new DataGridResult(total, pageInfo.getList());
        return R.ok(result);

    }

    /**
     * 授权页面的菜单树
     */
    @RequestMapping("/system/role/menu_tree")
    public R menuTree() {
        R r = menuService.selectMenu();
        return r;
    }

    /**
     * 授权
     *
     * @param roleId  角色id
     * @param menuIds 菜单
     */
    @RequestMapping("/system/role/assign_menu")
    public AjaxMessage assign(Integer roleId, Integer[] menuIds) {
        try {
            roleService.addRoleMenu(roleId, menuIds);
            return new AjaxMessage(true, "分配成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "分配失败");
        }
    }

    /**
     * 获取角色已有的菜单，用于回填选中页面上的菜单
     *
     * @param roleId
     */
    @RequestMapping("/system/role/role_menu/{roleId}")
    public List<Integer> roleMenu(@PathVariable("roleId") Integer roleId) {
        return roleService.getRoleMenuIds(roleId);
    }

    /**
     * 打开修改页面
     *
     * @param id
     */
    @RequestMapping("/system/role/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        TRole roleId = roleService.getRoleId(id);
        return R.ok().put("role", roleId);
    }

    @RequestMapping("/system/role/update")
    public AjaxMessage edit(@RequestBody TRole role) {
        try {
            roleService.updateRole(role);
            return new AjaxMessage(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "编辑失败");
        }
    }

    @RequestMapping("/system/role/add")
    public AjaxMessage add(@RequestBody TRole role) {
        try {
            roleService.addRole(role);
            return new AjaxMessage(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "添加失败");
        }
    }

    @RequestMapping("/system/role/delete")
    public AjaxMessage delete(@RequestBody int[] ids) {
        try {
            for (int id : ids) {
                roleService.deleteRole(id);
            }
            return new AjaxMessage(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "删除失败");
        }
    }
}
