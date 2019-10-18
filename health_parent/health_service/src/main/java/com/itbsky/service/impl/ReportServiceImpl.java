package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itbsky.dao.MemberDao;
import com.itbsky.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/10/14 10:26
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {


    @Autowired
    private MemberDao memberDao;

    /***
     * 性别分析
     * @return
     */
    @Override
    public List<Map<String, Object>> getGender() {
        return memberDao.getGender();
    }

    /**
     * 年龄分析
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAge() {
        return memberDao.getAge();
    }


}
