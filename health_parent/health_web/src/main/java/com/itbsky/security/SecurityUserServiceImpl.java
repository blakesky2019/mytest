package com.itbsky.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.pojo.Permission;
import com.itbsky.pojo.Role;
import com.itbsky.pojo.User;
import com.itbsky.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 包名:com.itbsky.security
 * 作者:龙在江湖
 * 日期:2019/10/8 16:18
 */
@Component("securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = null;
        Set<Role> roles = user.getRoles();

        //角色授权
        if (null != roles) {
            for (Role role : roles) {
                grantedAuthority = new SimpleGrantedAuthority(role.getKeyword());
                authorities.add(grantedAuthority);

                //权限授权
                Set<Permission> permissions = role.getPermissions();
                if (null != permissions) {
                    for (Permission permission : permissions) {

                        grantedAuthority = new SimpleGrantedAuthority(permission.getKeyword());
                        authorities.add(grantedAuthority);

                    }
                }
            }
        }

        //springsecurity框架会自动帮我们验证密码是否正确,不需要自己验证
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return userDetails;
    }
}
