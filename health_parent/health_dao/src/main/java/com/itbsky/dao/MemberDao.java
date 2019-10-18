package com.itbsky.dao;

import com.itbsky.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.dao
 * 作者:龙在江湖
 * 日期:2019/9/29 10:35
 */
public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void addMember(Member member);


    Integer findMembersByMonth( String month);

    List<Map<String,Object>>  getPackageReport();

    Integer findNewMemberByDate(String today);

    Integer findTotalMember();

    Integer findNewMemberAfterDate(String date);

    Integer findMemberCountBeforeDate(String date);

    List<Map<String,Object>> getGender();

    List<Map<String,Object>> getAge();

}
