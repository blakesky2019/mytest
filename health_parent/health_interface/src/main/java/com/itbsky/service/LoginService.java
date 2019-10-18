package com.itbsky.service;

import com.itbsky.pojo.Member;

/**
 * 包名:com.itbsky.service
 * 作者:龙在江湖
 * 日期:2019/10/6 12:25
 */
public interface LoginService {

    Member findMemberByTelephone(String telephone);

    void addMember(Member member);
}
