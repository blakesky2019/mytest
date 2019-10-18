package com.itbsky.dao;


import com.github.pagehelper.Page;
import com.itbsky.pojo.CheckGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/9/23 12:08
 */
public interface CheckGroupDao {

    @Insert("insert into t_checkgroup values(null,#{code},#{name},#{helpCode},#{sex},#{remark},#{attention})")
    @SelectKey(keyProperty ="id",keyColumn = "id",before = false,resultType = int.class,statement = "SELECT LAST_INSERT_ID()")
   void addCheckGroup(CheckGroup checkGroup);

    @Insert("insert into t_checkgroup_checkitem values(#{checkGroupId},#{checkItemId})")
    void addCheckGroupCheckItemId(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);


    Page<CheckGroup> findCheckGroupPage(String queryString);

    @Delete("delete from t_checkgroup where id=#{id} ")
    void deleteCheckGroupById(Integer id);

    @Delete("delete from  t_checkgroup_checkitem where checkgroup_id=#{id}")
    void deleteCheckGroupCheckItemById(Integer id);

    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id=#{id}")
    Integer[] findCheckItemIds(Integer id);


    @Select("select * from t_checkgroup where id=#{id}")
    CheckGroup findCheckGroupById(Integer id);

    @Update("update t_checkgroup set code=#{code},name=#{name},helpCode=#{helpCode},sex=#{sex},remark=#{remark},attention=#{attention} where id=#{id}")
    void updateCheckGroup(CheckGroup checkGroup);


    @Select("SELECT COUNT(1) FROM   t_checkgroup_checkitem WHERE checkgroup_id=#{checkGroupId}")
    int findDataById(Integer checkGroupId);


    @Select("select * from t_checkgroup")
    List<CheckGroup> findAll();

    @Select("SELECT COUNT(1) FROM t_package_checkgroup WHERE package_id=#{id}")
    int findPackageCheckGroupById(Integer id);
}
