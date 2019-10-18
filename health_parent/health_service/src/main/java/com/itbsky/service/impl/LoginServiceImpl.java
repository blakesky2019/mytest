package com.itbsky.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itbsky.dao.LoginDao;
import com.itbsky.pojo.Member;
import com.itbsky.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 包名:com.itbsky.service.impl
 * 作者:龙在江湖
 * 日期:2019/10/6 12:26
 */
@Service(interfaceClass= LoginService.class)
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;
    @Override
    public Member findMemberByTelephone(String telephone) {
        return loginDao.findMemberByTelephone(telephone);
    }

    @Override
    public void addMember(Member member) {
        loginDao.addMember(member);
    }
}
