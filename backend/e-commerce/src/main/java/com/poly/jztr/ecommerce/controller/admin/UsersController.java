package com.poly.jztr.ecommerce.controller.admin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin controller")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/admin/users")
public class UsersController {
    @GetMapping
    public String test(){
       return "";
    }
}
