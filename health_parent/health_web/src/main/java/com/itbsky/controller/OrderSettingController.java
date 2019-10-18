package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.Result;
import com.itbsky.pojo.OrderSetting;
import com.itbsky.service.OrderSettingService;
import com.itbsky.utils.POIUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/26 21:58
 */
@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("excelFile") MultipartFile excelFile) throws Exception {

        List<String[]> excelFiles = POIUtils.readExcel(excelFile);
        List<OrderSetting> orderSettingList = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);


        if (null != excelFiles && excelFiles.size() > 0) {
            OrderSetting orderSetting = null;
            for (String[] file : excelFiles) {
                orderSetting = new OrderSetting(simpleDateFormat.parse(file[0]), Integer.valueOf(file[1]));
                orderSettingList.add(orderSetting);
            }
        }

        if (orderSettingList.size() > 0) {
            orderSettingService.uploadFile(orderSettingList);
        }

        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @PostMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {

        List<OrderSetting> orderSettingList = orderSettingService.getOrderSettingByMonth(month);

        List<Map<String, Object>> mapList = new ArrayList<>();
        if (null != orderSettingList && orderSettingList.size() > 0) {
            Map<String, Object> map = null;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("d");
            for (OrderSetting orderSetting : orderSettingList) {
                map = new HashMap<>();
                map.put("date", Integer.valueOf(simpleDateFormat.format(orderSetting.getOrderDate())));
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                mapList.add(map);
            }
        }

        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, mapList);
    }

    @PostMapping("/orderday")
    public Result orderDay(String day,Integer number) throws ParseException {

        orderSettingService.orderDay(day,number);

        return new Result(true, MessageConstant.ORDER_SUCCESS);
    }

}
