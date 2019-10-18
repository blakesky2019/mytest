package com.itbsky.security;

import com.itbsky.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 包名:com.itbsky.security
 * 作者:龙在江湖
 * 日期:2019/10/7 14:04
 */
public class SecurityUserService implements UserDetailsService{



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=findUserByName(username);

        List<GrantedAuthority>authorities=new ArrayList<>();

        GrantedAuthority grantedAuthority=new SimpleGrantedAuthority("ROLE_ADMIN");
        grantedAuthority=new SimpleGrantedAuthority("add");
        authorities.add(grantedAuthority);


        UserDetails userDetails=new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        return userDetails;
    }


    private User findUserByName(String username){
       User user=null;
        if("admin".equals(username)){
            user = new User();
            user.setUsername(username);
            user.setPassword("$2a$10$Fv4araq0mXKV0YAKCmG.B.cyQT.C9pY5oqK3XFHBzDNr9EHXM34TW");//password:123456

        }
        return user;
    }

     //public static void main(String[] args) {
     //    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
     //
     //    System.out.println(encoder.matches("123456","$2a$10$AHmBtV7couFQ0q/IT2oEqOzDB5g5Nxx4bfT.tlvNv5rFyDYlCrHB2"));
     //}
}
