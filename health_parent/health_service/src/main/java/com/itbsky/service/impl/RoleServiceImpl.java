package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbsky.dao.RoleDao;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Role;
import com.itbsky.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageResult<Role> findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Role> page = roleDao.findpage(queryPageBean.getQueryString());
        PageResult<Role> pageResult = new PageResult<Role>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.addrole(role);
        Integer roleId = role.getId();
        if (null != permissionIds) {
            for (Integer permissionId : permissionIds) {
                roleDao.addRoleAndPermissionId(roleId,permissionId);
            }
        }
        if (null != menuIds) {
            for (Integer menuId : menuIds) {
                roleDao.addRoleAndMenuId(roleId,menuId);
            }
        }
    }

    @Override
    public void updateRole(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.updateRole(role);
        Integer roleId = role.getId();
        roleDao.deletePermissionIdByRoleId(roleId);
        if (null != permissionIds) {
            for (Integer permissionId : permissionIds) {
                roleDao.addRoleAndPermissionId(roleId,permissionId);
            }
        }
        roleDao.deleteMenuIdByRoleId(roleId);
        if (null != menuIds) {
            for (Integer menuId : menuIds) {
                roleDao.addRoleAndMenuId(roleId,menuId);
            }
        }
    }

    @Override
    public void deleteById(int id) {
        int count = roleDao.findCount(id);
        if (count > 0) {
            roleDao.deletePermissionIdById(id);
            roleDao.deleteMenuIdById(id);
            roleDao.deleteById(id);
        }
    }

    @Override
    public Role findById(int id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(int id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    @Override
    public List<Integer> findMenuIdsByRoleId(int id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
