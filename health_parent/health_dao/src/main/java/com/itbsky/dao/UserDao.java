package com.itbsky.dao;

import com.github.pagehelper.Page;
import com.itbsky.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/10/8 17:09
 */
public interface UserDao {
    User findUserByUserName(String username);

    @Select("<script>"+"select * from t_user "+"<if test='value != null and value.length > 0'> where username like #{value} </if>"+"</script>")
    Page<User> findpage(String queryString);

    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = "SELECT last_insert_id()")
    @Insert("insert into t_user values (null, #{birthday},#{gender},#{username},#{password},#{remark},#{station},#{telephone})")
    void addUser(User user);

    @Insert("insert into t_user_role values (#{userId},#{roleId})")
    void addUserAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Select("select * from t_user where id = #{id}")
    User findById(int id);

    @Select("select role_id from t_user_role where user_id = #{id}")
    List<Integer> findRoleIdsByUserId(int id);

    @Update("update t_user set birthday=#{birthday},gender=#{gender},username=#{username},password=#{password},remark=#{remark},station=#{station},telephone=#{telephone} where id = #{id}")
    void editUser(User user);

    @Delete("delete from t_user_role where user_id = #{userId}")
    void deleteUserRole(Integer userId);

    @Select("select * from t_user where id = #{id}")
    int findcount(int id);

    @Delete("delete from t_user where id = #{id}")
    void deleteUserById(int id);
}
