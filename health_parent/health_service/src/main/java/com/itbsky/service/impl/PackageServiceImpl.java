package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbsky.dao.PackageDao;
import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Package;
import com.itbsky.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/9/25 23:08
 */
@Service(interfaceClass = PackageService.class)
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;


    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        Page<Package>page=packageDao.findPage(queryPageBean.getQueryString());

        return new PageResult<Package>(page.getTotal(),page.getResult());
    }

    @Override
    public void addPackage(Package hPackage, Integer[] checkgroupIds) {

        packageDao.addPackage(hPackage);

        if(null!=checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.addPackageCheckGroupIds(hPackage.getId(),checkgroupId);
            }
        }

    }

    @Override
    public List<Package> getPackage() {
        return packageDao.getPackage();
    }

    @Override
    public Package findById(Integer id) {
        return packageDao.findById(id);
    }

    @Override
    public Package findPackageById(Integer id) {
        return packageDao.findPackageById(id);
    }
}
