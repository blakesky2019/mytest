package com.itbsky.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 包名:com.itbsky.security
 * 作者:龙在江湖
 * 日期:2019/10/8 11:32
 */
@RestController
@RequestMapping("/hello")
@PreAuthorize("hasAuthority('add')")
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class HelloHandle {

    @RequestMapping("/add")
    public String add(){
        System.out.println("add.............");
        return null;
    }

    @RequestMapping("/hello")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello(){
        System.out.println("admin..........");
        return null;
    }
}
