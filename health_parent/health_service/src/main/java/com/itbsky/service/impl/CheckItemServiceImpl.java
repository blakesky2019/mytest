package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbsky.constant.MessageConstant;
import com.itbsky.dao.CheckItemDao;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.exception.MyException;
import com.itbsky.pojo.CheckItem;
import com.itbsky.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/9/22 15:45
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;



    /**
     * 查询分页数据
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        Page<CheckItem>page=checkItemDao.findPageByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem>pageResult=new PageResult<CheckItem>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.addCheckItem(checkItem);
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        int count=checkItemDao.findCountByCheckId(id);
        if(count>0){
            //有引用,不许删除
            throw new MyException(MessageConstant.DELETE_CHECKITEM_FAIL_USED);
        }
        checkItemDao.deleteById(id);
    }


    /**
     * 根据id查询数据,前端数据回写
     * @param id
     * @return
     */
    @Override
    public CheckItem findCheckItemById(Integer id) {
        return checkItemDao.findCheckItemById(id);
    }

    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkItemDao.updateCheckItem(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }
}
