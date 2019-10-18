package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.CheckItem;
import com.itbsky.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/22 14:25
 */
@RestController
@RequestMapping("/checkItem")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem>pageResult=checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @PostMapping("/add")
    //@PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result addCheckItem(@RequestBody CheckItem checkItem){

        checkItemService.addCheckItem(checkItem);

        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }


    @PostMapping("/delete")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Result deleteById( Integer id){


        checkItemService.deleteById(id);

        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findCheckItemById")
    public Result findCheckItemById( Integer id){


        CheckItem checkItem=checkItemService.findCheckItemById(id);

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @PostMapping("/updateCheckItem")
    public Result updateCheckItem(@RequestBody CheckItem checkItem){

       checkItemService.updateCheckItem(checkItem);

        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/findAll")
    public Result findAll(){

        List<CheckItem>checkItemList=checkItemService.findAll();


        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
    }


}
