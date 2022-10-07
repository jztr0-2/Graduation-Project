package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.LoginDto;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Optional;

@RestControllerAdvice("public/user")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/public/users")
public class UsersController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDto login) {
        Optional<User> user = service.findByEmail(login.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "login  successfully", jwtProvider.generateTokenLogin(login.getEmail())));

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(422, "password is incorrect", "")
            );
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ResponseObject(422, "email not found", "")
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody() @Validated UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setStatus(Constant.USER_STATUS_ACTIVATED);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "", service.save(user)));
    }
}
