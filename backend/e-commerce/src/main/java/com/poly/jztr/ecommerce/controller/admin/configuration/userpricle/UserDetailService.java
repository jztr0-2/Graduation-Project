package com.poly.jztr.ecommerce.controller.admin.configuration.userpricle;

import com.poly.jztr.ecommerce.model.Admin;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.common.service.AdminService;
import com.poly.jztr.ecommerce.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(username.contains("admin")){
            Admin admin = adminService.findByLoginName(username).get();
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            UserPrinciple userPrinciple = UserPrinciple.build(null, admin, grantedAuthorities);
            return userPrinciple;
        }
        User u = userService.findByEmail(username).get();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        UserPrinciple userPrinciple = UserPrinciple.build(u,null, grantedAuthorities);
        return userPrinciple;
    }
}
