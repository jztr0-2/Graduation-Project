package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.UserService;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("user controller")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/user/users")
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;
    @GetMapping()
    public ResponseEntity<ResponseObject> info(@RequestHeader(value = "Authorization") String jwt){
        User user = getUserByToken(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                Constant.RESPONSE_STATUS_SUCCESS, "Get user info", user));
    }

    private User getUserByToken(String jwt) {
        return userService.findByEmailOrPhone(jwtProvider.getUsernameFromToken(jwt));
    }
}
