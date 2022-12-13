package com.poly.jztr.ecommerce.controller.admin;


import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("admin controller")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/users")
public class UsersController {

    @Autowired
    UserService service;
    @GetMapping("/is_login")
    public ResponseEntity<ResponseObject> isAdminLogin(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Admin is logged in",
                        true)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllUser(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer perPage){
        Pageable pageable = CustomPageable.getPage(page,perPage);
        Page<User> page1 = service.findAll(pageable);
        PageableSerializer pageableSerializer = new PageableSerializer(page1);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get user successfully",
                        pageableSerializer)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get user successfully",
                        service.findById(id))
        );
    }
}
