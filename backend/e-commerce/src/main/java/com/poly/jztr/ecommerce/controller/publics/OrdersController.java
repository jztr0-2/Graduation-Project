package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.expection.QuantityIsTooLagerException;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("publics/order")
@RequestMapping("api/v1/public/orders")
public class OrdersController {
    @Autowired
    OrderService service;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody OrderDto dto) throws QuantityIsTooLagerException {
        User user = userService.findByEmail("jztr@gmail.com").get();
        try {
            Order order = service.toOrder(dto);
            order.setUser(user);
            order.setPaymentMethod(Constant.ORDER_PAYMENT_METHOD_COD);
            order = service.save(order);
            service.updateQuantity(dto.getItems());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    Constant.RESPONSE_STATUS_SUCCESS, "Create order successfully", order
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuantityIsTooLagerException();
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByPhone(@RequestParam(defaultValue = "") String phone,
        @RequestParam("limit") Optional<Integer> limit, @RequestParam("curPage") Optional<Integer> curPage
    ){
        if(phone.trim().startsWith("0")) {
            phone = "84" + phone.trim().substring(1);
        }
        Pageable pageable = CustomPageable.getPage(curPage.orElse(1), limit.orElse(8));
        Page<Order> orders = service.findByPhone(phone, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Find order by phone",
                        new PageableSerializer(orders)));
    }
    @GetMapping("/find")
    public ResponseEntity<ResponseObject> findByOrderId(
            @RequestParam("id") Long id
    ) {
        Optional<Order> order = service.findById(id);
        if (order.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND, "NOT FOUND",""));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK", order.get())
            );
        }

    }}
