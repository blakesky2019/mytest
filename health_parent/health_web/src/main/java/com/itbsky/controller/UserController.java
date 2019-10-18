package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.User;
import com.itbsky.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.SecurityPermission;
import java.util.List;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/10/9 11:57
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping("/getUserName")
    public Result getUserName(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<User> pageResult = userService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,pageResult);
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds){
        userService.add(user,roleIds);
        return new Result(true,MessageConstant.ADD_USER_SUCCESS);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        User user = userService.findById(id);
        return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
    }


    @GetMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(int id){
        List<Integer> roleIds = userService.findRoleIdsByUserId(id);
        return new Result(true,MessageConstant.QUERY_USER_SUCCESS,roleIds);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody User user,Integer[] roleIds){
        userService.edit(user,roleIds);
        return new Result(true,MessageConstant.EDIT_UDER_SUCCESS);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        userService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
    }
}
