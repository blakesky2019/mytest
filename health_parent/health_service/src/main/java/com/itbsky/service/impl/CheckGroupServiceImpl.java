package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbsky.dao.CheckGroupDao;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.exception.MyException;
import com.itbsky.pojo.CheckGroup;
import com.itbsky.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/9/23 12:06
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupDao.addCheckGroup(checkGroup);

        if(null!=checkGroup.getId()){
            Integer checkGroupId=checkGroup.getId();
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItemId(checkGroupId,checkitemId);
            }
        }
    }


    @Override
    public PageResult<CheckGroup> findCheckGroupPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup>page=checkGroupDao.findCheckGroupPage(queryPageBean.getQueryString());

        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }


    @Override
    public void deleteById(Integer id) {

        int count=checkGroupDao.findPackageCheckGroupById(id);

        if(count>0){
            throw new MyException("删除检查组已经被使用了");
        }else{
            checkGroupDao.deleteCheckGroupCheckItemById(id);

            checkGroupDao.deleteCheckGroupById(id);

        }


    }

    @Override
    public Integer[] findCheckItemIds(Integer id) {
        Integer[] checkItemIds=checkGroupDao.findCheckItemIds(id);
        return  checkItemIds;
    }

    @Override
    public CheckGroup findCheckGroupById(Integer id) {

        return  checkGroupDao.findCheckGroupById(id);
    }

    @Override
    public void updateCheckGroup(CheckGroup checkGroup,Integer[]checkitemIds) {

        //修改检查组
        checkGroupDao.updateCheckGroup(checkGroup);


        /*
        *checkitemIds之前有数据,但没修改;
        *
        * checkitemIds之前有数据,但已修改;
        *
        * checkitemIds之前无数据,但无修改;
        *
        * checkitemIds之前无数据,但已修改;
        *
        * */
        Integer checkGroupId=checkGroup.getId();

        //int count=checkGroupDao.findDataById(checkGroupId);

        //如果用户没有勾选修改,直接跳过
        if(checkitemIds==null||checkitemIds.length<=0){
            return;
        }

//        if(count==0){
//            for (Integer checkitemId : checkitemIds) {
//                checkGroupDao.addCheckGroupCheckItemId(checkGroupId,checkitemId);
//            }
//        }else{
            //删除之前勾选的检查项
            checkGroupDao.deleteCheckGroupCheckItemById(checkGroupId);

            //增加新修改的检查项
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItemId(checkGroupId,checkitemId);
            }

        //}






    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
