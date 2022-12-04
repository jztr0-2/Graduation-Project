package com.poly.jztr.ecommerce.controller.admin;


import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin controller")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/users")
public class UsersController {
    @GetMapping
    public ResponseEntity<ResponseObject> isAdminLogin(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Admin is logged in",
                        true)
        );
    }
}
