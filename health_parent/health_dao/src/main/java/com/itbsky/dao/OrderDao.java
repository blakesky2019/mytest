package com.itbsky.dao;

import com.itbsky.pojo.Order;
import com.itbsky.pojo.Package;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/9/28 19:43
 */
public interface OrderDao {

    @Select("select * from t_package where id=#{id}")
    Package findPackageById(Integer id);

    List<Order> findOrderByOrder(Order order);

    void addOrder(Order order);

    Map<String,Object> findOrderInforByOrderId(int id);


    /**
     * 今日预约人数
     * @param today
     * @return
     */
    Integer findOrderNumberToday(String today);


    /**
     * 今日到诊人数
     * @param today
     * @return
     */
    Integer findVisitsNumberToday(String today);


    /**
     * 查询某日之后的预约人数
     * @param day
     * @return
     */
    Integer findOrderNumberAfterDate(String day);


    /**
     * 查询某日之后的到诊人数
     * @param day
     * @return
     */
    Integer findVisitsNumberAfterDate(String day);

}
