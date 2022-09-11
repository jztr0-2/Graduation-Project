package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("public/user")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/public")
public class UsersController {
    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/login")
    public String login(){
        return jwtProvider.generateTokenLogin("A");
    }
}
