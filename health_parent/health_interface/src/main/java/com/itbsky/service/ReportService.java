package com.itbsky.service;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/10/14 10:25
 */
public interface ReportService {
    List<Map<String,Object>> getGender();

    List<Map<String,Object>> getAge();
}
