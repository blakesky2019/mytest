package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itbsky.dao.OrderSettingDao;
import com.itbsky.pojo.OrderSetting;
import com.itbsky.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/9/26 22:19
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;


    @Override
    public void uploadFile(List<OrderSetting> orderSettingList) {

        for (OrderSetting orderSetting : orderSettingList) {
            OrderSetting orderSettingByDate = orderSettingDao.findOrderSettingByDate(orderSetting.getOrderDate());
            if (null == orderSettingByDate) {
                orderSettingDao.addOrderSetting(orderSetting);
            } else {
                orderSettingByDate.setNumber(orderSetting.getNumber());
                orderSettingDao.updateOrderSetting(orderSettingByDate);
            }
        }
    }


    @Override
    public List<OrderSetting> getOrderSettingByMonth(String month) {
        String startDate = month + "-" + 01;
        String endDate = month + "-" + 31;
        return orderSettingDao.getOrderSettingByMonth(startDate, endDate);
    }

    @Override
    public void orderDay(String day, Integer number) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDate(date);
        if (null != orderSetting) {
            orderSetting.setNumber(number);
            orderSettingDao.updateOrderSetting(orderSetting);
        } else {
            orderSetting=new OrderSetting(date,number);
            orderSettingDao.addOrderSetting(orderSetting);
        }
    }

    @Override
    public void clearOrderSetting(String firstDay4ThisMonth) {
        orderSettingDao.clearOrderSetting(firstDay4ThisMonth);
    }


}
