package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.constant.RedisMessageConstant;
import com.itbsky.entity.Result;
import com.itbsky.exception.MyException;
import com.itbsky.pojo.Package;
import com.itbsky.service.OrderService;
import com.itbsky.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/28 19:38
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @PostMapping("/findPackageById")
    public Result findPackageById(Integer id){
        Package aPackage=orderService.findPackageById(id);
        aPackage.setImg("http://"+ QiNiuUtil.DOMAIN+"/"+aPackage.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,aPackage);
    }

    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String>orderInfo){

        Jedis jedis = jedisPool.getResource();

        String telephone=orderInfo.get("telephone");
        String key= RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        String code=jedis.get(key);
        if(null==code){
            return new Result(false,MessageConstant.NOT_SEND_CODE);
        }
        if(!code.equals(orderInfo.get("validateCode"))){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }

        int orderId=orderService.order(orderInfo);
        jedis.close();
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderId);
    }

    @PostMapping("/findById")
    public Result findOrderInforByOrderId(int id){
        Map<String,Object>map=orderService.findOrderInforByOrderId(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
    }


}
