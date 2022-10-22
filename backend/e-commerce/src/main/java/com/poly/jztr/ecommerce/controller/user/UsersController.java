package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("user controller")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/user/users")
public class UsersController {

}
