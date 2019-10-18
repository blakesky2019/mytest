package com.itbsky.service;


import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Role;

import java.util.List;

public interface RoleService {
    PageResult<Role> findPage(QueryPageBean queryPageBean);

    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    void updateRole(Role role, Integer[] permissionIds, Integer[] menuIds);

    void deleteById(int id);

    Role findById(int id);

    List<Integer> findPermissionIdsByRoleId(int id);

    List<Integer> findMenuIdsByRoleId(int id);

    List<Role> findAll();
}
