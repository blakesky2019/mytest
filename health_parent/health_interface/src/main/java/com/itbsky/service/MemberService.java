package com.itbsky.service;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/10/9 12:33
 */
public interface MemberService {
    Map<String,Object> getMemberReport();

    Map<String,Object> getPackageReport();

    Map<String,Object> getBusinessReportData();

    List<Integer> findMemberCount(List<String> dateBetween);
}
