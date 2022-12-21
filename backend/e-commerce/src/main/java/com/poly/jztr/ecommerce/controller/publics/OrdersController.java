package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.expection.QuantityIsTooLagerException;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("publics/order")
@RequestMapping("api/v1/public/orders")
public class OrdersController {
    @Autowired
    OrderService service;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody OrderDto dto) throws QuantityIsTooLagerException {
        try {
            Order order = service.toOrder(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    Constant.RESPONSE_STATUS_SUCCESS, "Create order successfully", service.save(order)
            ));
        } catch (Exception e) {
            throw new QuantityIsTooLagerException();
        }
    }
}
