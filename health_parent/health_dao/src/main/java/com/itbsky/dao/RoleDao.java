package com.itbsky.dao;


import com.github.pagehelper.Page;
import com.itbsky.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {
    @Select("<script>"+"select * from t_role "+"<if test='value != null and value.length > 0'> where keyword like #{value} or name like #{value} </if>"+"</script>")
    Page<Role> findpage(String queryString);


    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = "SELECT last_insert_id()")
    @Insert("insert into t_role values (null,#{name},#{keyword},#{description})")
    void addrole(Role role);

    @Insert("insert into t_role_permission values (#{roleId},#{permissionId})")
    void addRoleAndPermissionId(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);


    @Insert("insert into t_role_menu values (#{roleId},#{menuId})")
    void addRoleAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    @Update("update t_role set name=#{name},keyword=#{keyword},description=#{description} where id = #{id}")
    void updateRole(Role role);

    @Delete("delete from t_role_permission where role_id = #{roleId}")
    void deletePermissionIdByRoleId(Integer roleId);

    @Delete("delete from t_role_menu where role_id = #{roleId}")
    void deleteMenuIdByRoleId(Integer roleId);

    @Select("select count(id) from t_role where id = #{id}")
    int findCount(int id);

    @Delete("delete from t_role_permission where role_id = #{id}")
    void deletePermissionIdById(int id);

    @Delete("delete from t_role_menu where role_id = #{id}")
    void deleteMenuIdById(int id);

    @Select("select * from t_role where id = #{id}")
    Role findById(int id);

    @Delete("delete from t_role where id = #{id}")
    void deleteById(int id);

    @Select("select permission_id from t_role_permission where role_id = #{id}")
    List<Integer> findPermissionIdsByRoleId(int id);

    @Select("select menu_id from t_role_menu where role_id = #{id}")
    List<Integer> findMenuIdsByRoleId(int id);

    @Select("select * from t_role")
    List<Role> findAll();
}
