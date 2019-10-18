package com.itbsky.service;

import com.itbsky.pojo.OrderSetting;

import java.util.List; /**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/9/26 22:17
 */
public interface OrderSettingService {
    void uploadFile(List<OrderSetting> orderSettingList);

    List<OrderSetting> getOrderSettingByMonth(String month);

    void orderDay(String day, Integer number);

    void clearOrderSetting(String firstDay4ThisMonth);
}
