package com.itbsky.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.Role;
import com.itbsky.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Role> pageResult = roleService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,pageResult);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role,Integer[] permissionIds,Integer[] menuIds){
        roleService.add(role,permissionIds,menuIds);
        return new Result(true,MessageConstant.ADD_ROLE_SUCCESS);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Role role,Integer[] permissionIds,Integer[] menuIds){
        roleService.updateRole(role,permissionIds,menuIds);
        return new Result(true,MessageConstant.EDIT_ROLE_SUCCESS);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        roleService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        Role role = roleService.findById(id);
        return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,role);
    }

    @GetMapping("/findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(int id){
        List<Integer> permissionIds = roleService.findPermissionIdsByRoleId(id);
        return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,permissionIds);
    }

    @GetMapping("/findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(int id){
        List<Integer> menuIds = roleService.findMenuIdsByRoleId(id);
        return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,menuIds);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<Role> roleList = roleService.findAll();
        return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roleList);
    }

}
