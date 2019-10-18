package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itbsky.constant.MessageConstant;
import com.itbsky.dao.MemberDao;
import com.itbsky.dao.OrderDao;
import com.itbsky.dao.OrderSettingDao;
import com.itbsky.exception.MyException;
import com.itbsky.pojo.Member;
import com.itbsky.pojo.Order;
import com.itbsky.pojo.OrderSetting;
import com.itbsky.pojo.Package;
import com.itbsky.service.OrderService;
import com.itbsky.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/9/28 19:41
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Override
    public Package findPackageById(Integer id) {
        return orderDao.findPackageById(id);
    }

    @Override
    public int order(Map<String, String> orderInfo) {

        String orderDate=orderInfo.get("orderDate");
        Date date= null;
        try {
            date = DateUtils.parseString2Date(orderDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OrderSetting orderSetting=orderSettingDao.findOrderSettingByDate(date);

        //判断所选日期是否可以预约
        if(null==orderSetting){
            throw new  MyException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //判断预约人数是否已经满员
        if(orderSetting.getReservations()>=orderSetting.getNumber()){
            throw new  MyException(MessageConstant.ORDER_FULL);
        }

        String telephone=orderInfo.get("telephone");
        Member member=memberDao.findMemberByTelephone(telephone);

        //判断是否是会员,不是就创建会员
        if(null==member){
            member=new Member();
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            member.setIdCard(orderInfo.get("idCard"));
            member.setPhoneNumber(orderInfo.get("telephone"));
            member.setRegTime(date);
            memberDao.addMember(member);
        }

        Order order=new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setPackageId(Integer.valueOf(orderInfo.get("packageId")));
        List<Order>orderList=orderDao.findOrderByOrder(order);

        //判断是否是重复预约
        if(null!=orderList&&orderList.size()>0){
            throw new MyException(MessageConstant.HAS_ORDERED);
        }

        //以上都不是,往数据库添加数据,完成预约
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        orderDao.addOrder(order);

        //添加预约完成,更新当天已经预约人数
        orderSettingDao.updateOrderNumber(date);
        return order.getId();
    }

    @Override
    public Map<String, Object> findOrderInforByOrderId(int id) {
        return orderDao.findOrderInforByOrderId(id);
    }
}
