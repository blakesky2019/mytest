package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.entity.Result;
import com.itbsky.pojo.CheckGroup;
import com.itbsky.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/23 11:59
 */
@RestController
@RequestMapping("/checkGroup")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/addCheckGroup")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup, Integer[]checkitemIds){

        checkGroupService.addCheckGroup(checkGroup,checkitemIds);

        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/findCheckGroupPage")
    public Result findCheckGroupPage(@RequestBody QueryPageBean queryPageBean){

        PageResult<CheckGroup>pageResult=checkGroupService.findCheckGroupPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }


    @PostMapping("/deleteById")
    public Result deleteById(Integer id){

        checkGroupService.deleteById(id);

        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/findCheckItemIds")
    public Result findCheckItemIds(Integer id){

        Integer[]findcheckItemIds=checkGroupService.findCheckItemIds(id);

        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS,findcheckItemIds);
    }


    @PostMapping("/findCheckGroupById")
    public Result findCheckGroupById(Integer id){

        CheckGroup checkGroup=checkGroupService.findCheckGroupById(id);

        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS,checkGroup);
    }


    @PostMapping("/updateCheckGroup")
    public Result updateCheckGroup(@RequestBody CheckGroup checkGroup, Integer[]checkitemIds){

       checkGroupService.updateCheckGroup(checkGroup,checkitemIds);

        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/findAll")
    public Result findAll(){

        List<CheckGroup>checkGroupList=checkGroupService.findAll();

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
    }

}
