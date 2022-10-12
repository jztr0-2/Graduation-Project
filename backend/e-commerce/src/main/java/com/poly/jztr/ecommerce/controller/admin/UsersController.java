package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.LoginDto;
import com.poly.jztr.ecommerce.model.Admin;
import com.poly.jztr.ecommerce.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("admin controller")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/admin/users")
public class UsersController {

}
