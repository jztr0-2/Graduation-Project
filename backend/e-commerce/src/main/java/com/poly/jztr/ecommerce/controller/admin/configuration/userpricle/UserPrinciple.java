package com.poly.jztr.ecommerce.controller.admin.configuration.userpricle;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.jztr.ecommerce.model.Admin;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserPrinciple implements UserDetails {
    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    public UserPrinciple(Integer id, String username, String password, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;

//        roleArr = (String[]) this.getAuthorities().toArray();
    }

    public UserPrinciple() {
    }

    private String viewName;


    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    private String[] roleArr;

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id + "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserPrinciple build(User customer, Admin admin, Set<GrantedAuthority> roles) {
        Long id = 1L;
        String name;
        if (customer != null) {
            id = customer.getId();
            name = customer.getFirstName() + " " + customer.getLastName();
        } else {
            id = Long.valueOf(admin.getId());
            name = admin.getLoginName();
        }

        UserPrinciple u = new UserPrinciple(Math.toIntExact(id), name,
                "Hide", roles);
        u.setRoles(roles);
        return u;
    }
}
