package com.poly.jztr.ecommerce.controller.admin;

<<<<<<< HEAD
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.LoginDto;
import com.poly.jztr.ecommerce.model.Admin;
import com.poly.jztr.ecommerce.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
=======
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> faab5c6 (Thanh commit code login)

@RestController("admin controller")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/admin/users")
public class UsersController {

}
