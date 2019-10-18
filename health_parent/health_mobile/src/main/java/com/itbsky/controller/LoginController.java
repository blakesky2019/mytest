package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.constant.RedisMessageConstant;
import com.itbsky.entity.Result;
import com.itbsky.pojo.Member;
import com.itbsky.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/10/6 11:59
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private LoginService loginService;

    @PostMapping("/check")
    public Result loginByValidateCode(@RequestBody Map<String,String> map, HttpServletResponse response){
        Jedis jedis = jedisPool.getResource();
        String telephone=map.get("telephone");
        String code=map.get("validateCode");
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        String validateCode = jedis.get(key);
        if(null==validateCode){
            return new Result(false, "请点击发送二维码");
        }

        if(!validateCode.equals(code)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }

        Member member=loginService.findMemberByTelephone(telephone);
        if(member==null){
            member=new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            loginService.addMember(member);
        }

        Cookie cookie=new Cookie("login_member_telephone",telephone);
        cookie.setMaxAge(60*60*24*7);
        cookie.setPath("/");
        response.addCookie(cookie);

        jedis.close();

        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
