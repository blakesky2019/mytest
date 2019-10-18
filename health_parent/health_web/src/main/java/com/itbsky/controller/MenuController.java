package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.Menu;
import com.itbsky.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/10/13 14:19
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @GetMapping("/findByUsername")
    public Result findByUsername(String username){
        List<Menu> menuList = menuService.findByUsername(username);
        return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuList);
    }


    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Menu> pageResult = menuService.findPage(queryPageBean);
        try {
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return new Result(true,MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_MENU_FAIL);
        }
    }

    @GetMapping("/findById")
    public Result findById(int id){
        try {
            Menu menu = menuService.findById(id);
            return new Result(true,MessageConstant.GET_MENU_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MENU_FAIL);
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        try {
            menuService.updateById(menu);
            return new Result(true,MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MENU_FAIL);
        }
    }


    @GetMapping("/delete")
    public Result delete(int id){
        try {
            menuService.delete(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MENU_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List<Menu> menuList = menuService.findAll();
        return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menuList);
    }

}
