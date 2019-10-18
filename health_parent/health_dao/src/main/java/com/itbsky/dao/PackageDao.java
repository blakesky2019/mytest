package com.itbsky.dao;

import com.github.pagehelper.Page;
import com.itbsky.pojo.Package;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/9/25 23:17
 */
public interface PackageDao {

    Page<Package> findPage(String queryString);


    @Insert("insert into t_package values(null,#{name},#{code},#{helpCode},#{sex},#{age},#{price},#{remark},#{attention},#{img})")
    @SelectKey(keyProperty = "id",keyColumn = "id",resultType = int.class,before = false,statement = "SELECT LAST_INSERT_ID()")
    void addPackage(Package hPackage);

    @Insert("insert into t_package_checkgroup values(#{packageId},#{checkGroupId})")
    void addPackageCheckGroupIds(@Param("packageId") Integer id, @Param("checkGroupId") Integer checkgroupId);

    @Select("select * from t_package")
    List<Package> getPackage();

    Package findById(Integer id);

    Package findPackageById(Integer id);


    /**
     * 查询前四个热门的套餐占比
     * @return
     */
    List<Map<String,Object>> findHotPackage();
}
