package com.itbsky.service;

import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.CheckGroup;

import java.util.List;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/9/23 12:06
 */
public interface CheckGroupService {

    void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findCheckGroupPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    Integer[] findCheckItemIds(Integer id);

    CheckGroup findCheckGroupById(Integer id);

    void updateCheckGroup(CheckGroup checkGroup,Integer[]checkitemIds);

    List<CheckGroup> findAll();
}
