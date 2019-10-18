package com.itbsky.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.Permission;
import com.itbsky.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/addPermission")
    public Result permission(@RequestBody Permission permission){
        permissionService.add(permission);
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    @RequestMapping("/findPermissionPage")
    public Result findPermissionPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Permission> pageResult = permissionService.findpage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,pageResult);
    }

    @RequestMapping("/findById")
    public Result findById(int id){
        Permission permission = permissionService.findById(id);
        return new Result(true,MessageConstant.GET_PERMISSION_SUCCESS,permission);
    }

    @RequestMapping("/updatePermission")
    public Result updatePermission(@RequestBody Permission permission){
        permissionService.update(permission);
        return new Result(true,MessageConstant.EDIT_PERMSSION_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result delete(int id){
        permissionService.delete(id);
        return new Result(true,MessageConstant.DELETE_PERMSSION_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List<Permission> permissionList = permissionService.findAll();
        return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,permissionList);
    }
}
