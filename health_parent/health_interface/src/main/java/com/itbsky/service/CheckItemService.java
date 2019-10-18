package com.itbsky.service;

import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.CheckItem;

import java.util.List;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/9/22 14:30
 */
public interface CheckItemService {
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void addCheckItem(CheckItem checkItem);

    void deleteById(Integer id);

    CheckItem findCheckItemById(Integer id);

    void updateCheckItem(CheckItem checkItem);

    List<CheckItem> findAll();
}
