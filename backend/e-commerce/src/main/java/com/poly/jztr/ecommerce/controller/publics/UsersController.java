package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Arrays;

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
    public String login(){
        return "A";
        //return jwtProvider.generateTokenLogin("A");
    }

    @GetMapping("/login")
    public String login2(){
        return "A";
        //return jwtProvider.generateTokenLogin("A");
    }
    @PostMapping (value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE
    , MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<ResponseObject> register(@RequestPart(value = "user", required = false) @Validated UserDto dto,
                                                   @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        UserDto userDto = dto;

        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setStatus(Constant.USER_STATUS_ACTIVATED);
        if(image!= null) {
            Path path= Paths.get("uploads");
            InputStream inputStream = image.getInputStream();
            String fileName = "image_"+ Instant.now().getEpochSecond()+".jpg";
            Files.copy(inputStream,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"",service.save(user)));
    }
}
