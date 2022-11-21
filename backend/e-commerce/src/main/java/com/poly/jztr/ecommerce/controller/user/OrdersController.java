package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice("user/orders")
@RequestMapping("api/v1/user/orders")
public class OrdersController {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    OrderService service;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestHeader(value = "Authorization") String jwt,
                                                 @RequestBody OrderDto dto) {
        Long userId = userService.findByEmail(jwtProvider.getUsernameFromToken(jwt)).get().getId();
        Order order = service.toOrder(dto);
        order.setUser(new User(userId));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                Constant.RESPONSE_STATUS_SUCCESS, "Create order successfully", service.save(order)
        ));
    }
}
