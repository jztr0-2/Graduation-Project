package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestControllerAdvice("admin/order")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/order")
public class OrderController {
    @Autowired
    OrderService service;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByStatus(@RequestParam(defaultValue = "-1") Integer status,
                                                     @RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer perPage){
        Pageable pageable = CustomPageable.getPage(page, perPage, "createdAt", Constant.SORT_DESC);
        if(status == -1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
                            service.findAll(pageable)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
                        service.findByStatusIs(status, pageable)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") Long id){
        Optional<Order> optOrder = service.findById(id);
        if(optOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Order By Id", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Find Order Success", service.findById(id)));
    }
    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> updateOrder(@PathVariable("id") Long id,
                                                      @RequestBody OrderDto dto){
        Optional<Order> optOrder = service.findById(id);
        if(optOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Order By Id", null));
        }
        Order order = optOrder.get();
        order.setStatus(dto.getStatus());
        order = service.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update order successfully", order));
    }

}