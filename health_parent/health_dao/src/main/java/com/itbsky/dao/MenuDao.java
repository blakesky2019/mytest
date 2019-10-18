package com.itbsky.dao;

import com.github.pagehelper.Page;
import com.itbsky.pojo.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/10/13 14:40
 */
public interface MenuDao {
    List<Menu> findByUsername(String username);

    @Select("<script>"+"select * from t_menu "+"<if test='value != null and value.length > 0'> where linkUrl like #{value} or name like #{value}  or description like #{value} </if>"+"</script>")
    Page<Menu> findpage(String queryString);

    @Insert("insert into t_menu values (null,#{name},#{linkUrl},#{path},#{priority},#{icon},#{description},#{parentMenuId},#{level})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = "SELECT last_insert_id()")
    void add(Menu menu);

    @Select("select * from t_menu where id = #{id}")
    Menu findById(int id);

    @Update("update t_menu set name=#{name},linkUrl=#{linkUrl},path=#{path},priority=#{priority},icon=#{icon},description=#{description},parentMenuId=#{parentMenuId},level=#{level} where id = #{id}")
    void updateById(Menu menu);

    @Delete("delete from t_menu where id = #{id}")
    void delete(int id);

    @Select("select * from t_menu")
    List<Menu> findAll();
}
