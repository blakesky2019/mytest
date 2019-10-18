package com.itbsky.dao;

import com.github.pagehelper.Page;
import com.itbsky.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/9/22 16:00
 */
public interface CheckItemDao {

    Page<CheckItem> findPageByCondition(String queryString);

    @Insert("insert into t_checkitem values(null,#{code},#{name},#{sex},#{age},#{price},#{type},#{remark},#{attention})")
    void addCheckItem(CheckItem checkItem);

    @Delete("delete from t_checkitem where id=#{id}")
    void deleteById(Integer id);

    @Select("select * from t_checkitem where id=#{id}")
    CheckItem findCheckItemById(Integer id);

    @Update("update t_checkitem set code=#{code},name=#{name},sex=#{sex},age=#{age},price=#{price},type=#{type},remark=#{remark},attention=#{attention} where id=#{id}")
    void updateCheckItem(CheckItem checkItem);


    @Select("SELECT COUNT(1) FROM t_checkgroup_checkitem  WHERE checkitem_id=#{id}")
    int findCountByCheckId(Integer id);

    @Select("select * from t_checkitem")
    List<CheckItem> findAll();
}
