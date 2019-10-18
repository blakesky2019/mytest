package com.itbsky.service;

import com.itbsky.pojo.Package;

import java.util.Map;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/9/28 19:41
 */
public interface OrderService {
    Package findPackageById(Integer id);

    int order(Map<String, String> orderInfo);

    Map<String,Object> findOrderInforByOrderId(int id);
}
