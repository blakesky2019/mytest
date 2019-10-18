package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itbsky.dao.MemberDao;
import com.itbsky.dao.OrderDao;
import com.itbsky.dao.PackageDao;
import com.itbsky.service.MemberService;
import com.itbsky.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/10/9 12:33
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PackageDao packageDao;

    @Override
    public Map<String, Object> getMemberReport() {

        //1.创建list存储月份
        List<String>months=new ArrayList<>();

        //2.创建list存储每个月的总会员数量
        List<Integer>memberCount=new ArrayList<>();


        //3.根据当前时间算出过去一年的时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
        String month="";
        for (int i = 1; i <= 12; i++) {

            //4从当前月份起将过去一年的每个月存储到list集合
            calendar.add(Calendar.MONTH,1);
            month=dateFormat.format(calendar.getTime());
            months.add(month);

            //5.查找数据库返回过去一年每个月的总会员数
            Integer members=memberDao.findMembersByMonth(month+"-31");
            memberCount.add(members);

        }

        Map<String, Object>map=new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);

        //6.将月份以及每月总数据list集合存储到map中,返回前端
        return map;
    }


    /**
     * 套餐的预约占比
     * @return
     */
    @Override
    public Map<String, Object> getPackageReport() {

        //查询套餐名字name,以及预约数量value
        List<Map<String,Object>>packageCount=memberDao.getPackageReport();
        List<String>packageNames=new ArrayList<>();

        //获取套餐的名字集合
        if(null!=packageCount){
            for (Map<String, Object> map : packageCount) {
                packageNames.add((String) map.get("name"));
            }
        }

        //将套餐的名字集合,以及名字对应的数量集合分别存入map当中
        Map<String,Object>result=new HashMap<>();
        result.put("packageNames",packageNames);
        result.put("packageCount",packageCount);

        return result;
    }


    //reportDate:null,
    //todayNewMember :0,
    //totalMember :0,
    //thisWeekNewMember :0,
    //thisMonthNewMember :0,
    //todayOrderNumber :0,
    //todayVisitsNumber :0,
    //thisWeekOrderNumber :0,
    //thisWeekVisitsNumber :0,
    //thisMonthOrderNumber :0,
    //thisMonthVisitsNumber :0,

    /**
     * 获取运营统计数据
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {

        Map<String, Object>result=new HashMap<>();


        String today = null;
        String thisWeekMonday = null;
        String thisFirstDateOfMonth=null;
        try {
            //获取今天的时间并存储到result集合当中
           today= DateUtils.parseDate2String(new Date(),"yyyy-MM-dd");
           result.put("reportDate",today);

           //获取本周星期一的时间
            thisWeekMonday=DateUtils.parseDate2String(DateUtils.getThisWeekMonday());

            //获取本月一号的时间
            thisFirstDateOfMonth=DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取本日新增会员数并存储到result集合当中
        Integer todayNewMember=memberDao.findNewMemberByDate(today);
        result.put("todayNewMember",todayNewMember);

        //获取总会员数存储到result集合当中
       Integer totalMember=memberDao.findTotalMember();
       result.put("totalMember",totalMember);

        //获取本周新增会员数并存储到result集合当中
        Integer thisWeekNewMember=memberDao.findNewMemberAfterDate(thisWeekMonday);
        result.put("thisWeekNewMember",thisWeekNewMember);


        //获取本月新增会员数并存储到result集合当中
        Integer thisMonthNewMember = memberDao.findNewMemberAfterDate(thisFirstDateOfMonth);
        result.put("thisMonthNewMember",thisMonthNewMember);


        //获取今日预约数并存储到result集合当中
        Integer todayOrderNumber=orderDao.findOrderNumberToday(today);
        result.put("todayOrderNumber",todayOrderNumber);


        //获取今日到诊人数并存储到result集合当中
        Integer todayVisitsNumber=orderDao.findVisitsNumberToday(today);
        result.put("todayVisitsNumber",todayVisitsNumber);



        //获取本周预约人数并存储到result集合当中
        Integer thisWeekOrderNumber=orderDao.findOrderNumberAfterDate(thisWeekMonday);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);


        //获取本周到诊人数并存储到result集合当中
        Integer thisWeekVisitsNumber=orderDao.findVisitsNumberAfterDate(thisWeekMonday);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);


        //获取本月预约人数并存储到result集合当中
        Integer thisMonthOrderNumber=orderDao.findOrderNumberAfterDate(thisWeekMonday);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);


        //获取本月到诊人数并存储到result集合当中
        Integer thisMonthVisitsNumber=orderDao.findVisitsNumberAfterDate(thisWeekMonday);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);



        //查询热门套餐前四,并存储到result集合当中去
        List<Map<String,Object>>hotPackage=packageDao.findHotPackage();
        result.put("hotPackage",hotPackage);

        return result;
    }


    @Override
    public List<Integer> findMemberCount(List<String> dateBetween) {
        List<Integer> list = new ArrayList<>();
        for (String date : dateBetween) {
            Integer dateCount = memberDao.findMemberCountBeforeDate(date);
            list.add(dateCount);
        }
        return list;
    }

}
