package com.itbsky.service;

import com.itbsky.entity.PageResult;
import com.itbsky.entity.QueryPageBean;
import com.itbsky.pojo.Package;

import java.util.List;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/9/25 23:08
 */
public interface PackageService {
    PageResult<Package> findPage(QueryPageBean queryPageBean);

    void addPackage(Package hPackage, Integer[] checkgroupIds);

    List<Package> getPackage();

    Package findById(Integer id);

    Package findPackageById(Integer id);
}
