package com.itbsky.service;

import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Menu;

import java.util.List;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/10/13 14:33
 */
public interface MenuService {

    List<Menu> findByUsername(String username);

    PageResult<Menu> findPage(QueryPageBean queryPageBean);

    void add(Menu menu);

    Menu findById(int id);

    void updateById(Menu menu);

    void delete(int id);

    List<Menu> findAll();
}
