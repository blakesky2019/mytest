package com.itbsky.dao;

import com.itbsky.pojo.OrderSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.Date;
import java.util.List;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/9/26 22:20
 */
public interface OrderSettingDao {

    @Select("select * from t_ordersetting where orderDate=#{orderDate}")
    OrderSetting findOrderSettingByDate(Date orderDate);

    @Insert("insert into t_ordersetting (orderDate,number) values(#{orderDate},#{number})")
    void addOrderSetting(OrderSetting orderSetting);

    @Update("update t_ordersetting set number=#{number} where id=#{id}")
    void updateOrderSetting(OrderSetting orderSetting);

    @Select("select * from t_ordersetting where orderDate BETWEEN #{startDate} and #{endDate}  ")
    List<OrderSetting> getOrderSettingByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Update("UPDATE t_ordersetting SET reservations=(IFNULL(reservations,0)+1) WHERE orderDate=#{day}")
    void orderDay(String day);


    void updateOrderNumber(Date date);

    void clearOrderSetting(String firstDay4ThisMonth);
}
