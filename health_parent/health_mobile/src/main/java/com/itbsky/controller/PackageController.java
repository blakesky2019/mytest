package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbsky.constant.MessageConstant;
import com.itbsky.constant.RedisConstant;
import com.itbsky.entity.Result;
import com.itbsky.pojo.Package;
import com.itbsky.service.PackageService;
import com.itbsky.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/27 21:31
 */
@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;

    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/getPackage")
    public Result getPackage() {
        List<Package> list = null;
        Jedis jedis = jedisPool.getResource();
        try {
            String key = RedisConstant.PACKAGE_PAGES;
            String jsonStr = jedis.get(RedisConstant.PACKAGE_PAGES);
            ObjectMapper mapper = new ObjectMapper();
            if (null == jsonStr) {
                list = packageService.getPackage();
                if (null != list) {
                    for (Package aPackage : list) {
                        aPackage.setImg("http://" + QiNiuUtil.DOMAIN + "/" + aPackage.getImg());
                    }
                }
                jsonStr = mapper.writeValueAsString(list);
                jedis.set(RedisConstant.PACKAGE_PAGES, jsonStr);
                return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, jsonStr);
            }
            return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PACKAGE_FAIL);
        } finally {
            jedis.close();
        }

        //List<Package> packageList = packageService.getPackage();
        //return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, packageList);
    }


    @PostMapping("/findById")
    public Result findById(Integer id) {
        Package aPackage = packageService.findById(id);
        aPackage.setImg("http://" + QiNiuUtil.DOMAIN + "/" + aPackage.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, aPackage);
    }

}
