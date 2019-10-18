package com.itbsky.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.service.OrderSettingService;
import com.itbsky.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;


public class ClearOrderSettingJob {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderSettingService orderSettingService;

    // 清理预约设置
    public void clearOrderSetting() {
        System.out.println("定时删除预约设置...");
        // 删除这个月之前的预约记录
        String firstDay4ThisMonth = null;
        try {
            firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderSettingService.clearOrderSetting(firstDay4ThisMonth);

    }

    public static void main(String[] args) {
        ApplicationContext al = new ClassPathXmlApplicationContext("classpath:spring-job.xml");
    }
}
