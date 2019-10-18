package com.itbsky.dao;

import com.itbsky.pojo.Member;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/10/6 12:28
 */
public interface LoginDao {
    Member findMemberByTelephone(String telephone);

    void addMember(Member member);
}
