package com.poly.jztr.ecommerce.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin controller")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/admin/users")
public class UsersController {
    @Operation(summary = "Get a book by its id")
    @GetMapping
    public String test(){
       return "";
    }
}
