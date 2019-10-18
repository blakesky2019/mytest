package com.itbsky.service;

import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.User;

import java.util.List;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/10/8 17:07
 */
public interface UserService {

    User findUserByUserName(String username);

    PageResult<User> findPage(QueryPageBean queryPageBean);

    void add(User user, Integer[] roleIds);

    User findById(int id);

    List<Integer> findRoleIdsByUserId(int id);

    void edit(User user, Integer[] roleIds);

    void deleteById(int id);
}
