package com.itbsky.service;


import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Permission;

import java.util.List;

public interface PermissionService {
    void add(Permission permission);

    PageResult<Permission> findpage(QueryPageBean queryPageBean);

    Permission findById(int id);

    void update(Permission permission);

    void delete(int id);

    List<Permission> findAll();
}
