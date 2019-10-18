package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbsky.dao.UserDao;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.User;
import com.itbsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/10/8 17:08
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }

    @Override
    public PageResult<User> findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<User> page = userDao.findpage(queryPageBean.getQueryString());
        PageResult<User> pageResult = new PageResult<User>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void add(User user, Integer[] roleIds) {
        userDao.addUser(user);
        Integer userId = user.getId();
        if (null != roleIds) {
            for (Integer roleId : roleIds) {
                userDao.addUserAndRoleId(userId,roleId);
            }
        }
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(int id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Override
    public void edit(User user, Integer[] roleIds) {
        userDao.editUser(user);
        Integer userId = user.getId();
        userDao.deleteUserRole(userId);
        if (null != roleIds) {
            for (Integer roleId : roleIds) {
                userDao.addUserAndRoleId(userId,roleId);
            }
        }
    }

    @Override
    public void deleteById(int id) {
        int count = userDao.findcount(id);
        if (count > 0) {
            userDao.deleteUserRole(id);
            userDao.deleteUserById(id);
        }
    }
}
