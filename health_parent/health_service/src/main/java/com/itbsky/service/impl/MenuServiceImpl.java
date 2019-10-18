package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbsky.dao.MenuDao;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Menu;
import com.itbsky.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/10/13 14:34
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findByUsername(String username) {
        List<Menu> allMenuList = menuDao.findByUsername(username);


        Map<Integer, Menu> resultMenu = getResultMenu(allMenuList);

        List<Menu> resultList = new ArrayList<>();
        for (Map.Entry<Integer, Menu> menuEntry : resultMenu.entrySet()) {

            //获取最大的父菜单,没有父菜单即为最大的父菜单
            if (menuEntry.getValue().getParentMenuId() == null) {
                resultList.add(menuEntry.getValue());
            }
        }
        return resultList;
    }

    private Map<Integer, Menu> getResultMenu(List<Menu> allMenuList) {
        Map<Integer, Menu> allMenuMap = new HashMap<>();
        List<Menu> children = null;
        for (Menu menu : allMenuList) {
            allMenuMap.put(menu.getId(), menu);
        }
        for (Menu menu : allMenuList) {
            if (menu.getParentMenuId() != null) {
                Menu parentMenu = allMenuMap.get(menu.getParentMenuId());
                children = parentMenu.getChildren();
                if (children == null) {
                    children = new ArrayList<>();

                }
                children.add(menu);
                parentMenu.setChildren(children);
            }
        }
        return allMenuMap;
    }

    @Override
    public PageResult<Menu> findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Menu> page = menuDao.findpage(queryPageBean.getQueryString());
        PageResult<Menu> pageResult = new PageResult<Menu>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public Menu findById(int id) {
        return menuDao.findById(id);
    }

    @Override
    public void updateById(Menu menu) {
        menuDao.updateById(menu);
    }

    @Override
    public void delete(int id) {
        menuDao.delete(id);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

}
