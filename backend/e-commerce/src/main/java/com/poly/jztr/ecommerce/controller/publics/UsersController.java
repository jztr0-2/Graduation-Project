package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.LoginDto;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.Admin;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.AdminService;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestControllerAdvice("public/user")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/users")
public class UsersController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AdminService adminService;
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDto login) {
        Optional<User> user = service.findByEmail(login.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "login  successfully", jwtProvider.generateTokenLogin(login.getEmail())));

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "password is incorrect", "")
            );
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "email not found", "")
        );
    }

    @PostMapping("admin-login")
    public ResponseEntity<ResponseObject> adminLogin(@RequestBody LoginDto login){
        Optional<Admin> admin = adminService.findByLoginName(login.getEmail());
        if(admin.isPresent()){
            if (passwordEncoder.matches(login.getPassword(), admin.get().getPassword()))
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "login  successfully", jwtProvider.generateTokenLoginAdmin(login.getEmail())));

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "password is incorrect", ""));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "username not found", ""));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody() @Validated UserDto dto) throws DuplicateEntryException {
        User user = new User();
        if(service.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Email", "Email is already exits");
        }
        BeanUtils.copyProperties(dto, user);
        user.setStatus(Constant.USER_STATUS_ACTIVATED);
        if (!dto.getPassword().equals(dto.getPasswordConfirmation()))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "Password not same", ""));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "", service.save(user)));
    }
}
