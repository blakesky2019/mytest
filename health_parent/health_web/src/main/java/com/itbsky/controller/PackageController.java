package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.constant.RedisConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.Package;
import com.itbsky.service.PackageService;
import com.itbsky.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/24 20:40
 */
@RestController
@RequestMapping("/package")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class PackageController {

    @Reference
    private PackageService packageService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/upLoadImage")
    public Result upLoadImage(@RequestParam("imgFile")MultipartFile imageFile){

        String originalFilename = imageFile.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        String newFileName=uuid.toString()+suffixName;

        try {

            QiNiuUtil.uploadViaByte(imageFile.getBytes(),newFileName);

            //将文件名传进redis中
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            jedis.close();

            Map<String,String> mapSuccess=new HashMap<>();

            mapSuccess.put("addressFileName",QiNiuUtil.DOMAIN);
            mapSuccess.put("newFileName",newFileName);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,mapSuccess);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PostMapping("/addPackage")
    public Result addPackage(@RequestBody Package hPackage, Integer[]checkgroupIds){

        Jedis jedis = jedisPool.getResource();
        jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,hPackage.getImg());
        jedis.close();

        packageService.addPackage(hPackage,checkgroupIds);

        return new Result(true, MessageConstant.ADD_PACKAGE_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        //System.out.println(packageService);

        PageResult<Package>pageResult=packageService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_PACKAGELIST_SUCCESS,pageResult);
    }


}
