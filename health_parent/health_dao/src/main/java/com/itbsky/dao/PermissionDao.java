package com.itbsky.dao;


import com.github.pagehelper.Page;
import com.itbsky.pojo.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionDao {

    @Insert("insert into t_permission values (null,#{name},#{keyword},#{description})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = "SELECT last_insert_id()")
    void add(Permission permission);

    @Select("<script>"+"select * from t_permission "+"<if test='value != null and value.length > 0'> where keyword like #{value} or name like #{value}  or description like #{value} </if>"+"</script>")
    Page<Permission> findpage(String queryString);

    @Select("select * from t_permission where id = #{id}")
    Permission findById(int id);

    @Update("update t_permission set name=#{name},keyword=#{keyword},description=#{description} where id = #{id}")
    void update(Permission permission);

    @Delete("delete from t_permission where id = #{id}")
    void delete(int id);

    @Select("select * from t_permission")
    List<Permission> findAll();
}
