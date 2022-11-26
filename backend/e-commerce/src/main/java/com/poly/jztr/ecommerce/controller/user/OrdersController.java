package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<ResponseObject> index(@RequestHeader(value = "Authorization") String jwt,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer perPage) {
        User user = getUserByToken(jwt);
        Pageable pageable = CustomPageable.getPage(page,perPage,"updatedAt",Constant.SORT_DESC);
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get order by user successfully",
                service.findByUser(pageable,user)));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestHeader(value = "Authorization") String jwt,
                                                 @RequestBody OrderDto dto) {
        Order order = service.toOrder(dto);
        order.setUser(getUserByToken(jwt));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                Constant.RESPONSE_STATUS_SUCCESS, "Create order successfully", service.save(order)
        ));
    }

    private User getUserByToken(String jwt) {
        return userService.findByEmail(jwtProvider.getUsernameFromToken(jwt)).get();
    }
}
