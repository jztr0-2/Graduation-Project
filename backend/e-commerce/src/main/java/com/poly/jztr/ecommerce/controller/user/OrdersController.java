package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.expection.QuantityIsTooLagerException;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.serializer.OrderAndPaySerializer;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.PromotionService;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestControllerAdvice("user/orders")
@RequestMapping("api/v1/user/orders")
public class OrdersController {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    OrderService service;

    @Autowired
    UserService userService;

    @Autowired
    PromotionService promotionService;
    @GetMapping
    public ResponseEntity<ResponseObject> index(@RequestHeader(value = "Authorization") String jwt,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer perPage) {
        User user = getUserByToken(jwt);
        Pageable pageable = CustomPageable.getPage(page, perPage, "updatedAt", Constant.SORT_DESC);
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get order by user successfully",
                service.findByUser(pageable, user)));
    }
    @GetMapping("/find/status")
    public ResponseEntity<ResponseObject> findByStatusOrder
            (   @RequestHeader(value = "Authorization") String jwt,
                @RequestParam("curPage") Optional<Integer> curPage,
                @RequestParam("limit") Optional<Integer> limit,
                @RequestParam("status") Optional<Integer> status )
    {
        User user = getUserByToken(jwt);
        Pageable pageable = CustomPageable.getPage(curPage.orElse(1), limit.orElse(10), "updatedAt", Constant.SORT_DESC);
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get order by user successfully",
                service.findByUserAndStatus(user, status.get(), pageable)));
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestHeader(value = "Authorization") String jwt,
                                                 @RequestBody OrderDto dto) throws QuantityIsTooLagerException{
        try {
            Order order = service.toOrder(dto);
            order.setUser(getUserByToken(jwt));
            order.setPaymentMethod(Constant.ORDER_PAYMENT_METHOD_COD);
            service.updateQuantity(dto.getItems());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    Constant.RESPONSE_STATUS_SUCCESS, "Create order successfully", service.save(order, dto.getPromoCode())

            ));
        } catch (Exception e){
            e.printStackTrace();
            throw new QuantityIsTooLagerException();
        }
    }

    @PostMapping("/order_and_pay")
    public ResponseEntity<ResponseObject> orderAndPay(@RequestHeader(value = "Authorization") String jwt,
                                        @RequestBody OrderDto dto) throws QuantityIsTooLagerException{
        try {
            User user = getUserByToken(jwt);
            Order order = service.toOrder(dto);
            order.setUser(user);
            order.setStatus(Constant.ORDER_STATUS_WAITING_FOR_PAYMENT);
            order.setPaymentMethod(Constant.ORDER_PAYMENT_METHOD_MOMO);
            order.setPaymentCode(UUID.randomUUID().toString());
            order = service.save(order, dto.getPromoCode());
            OrderAndPaySerializer orderAndPaySerializer = new OrderAndPaySerializer(order);
            service.checkPayment(order.getId());
            service.updateQuantity(dto.getItems());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    Constant.RESPONSE_STATUS_SUCCESS, "Create order successfully",orderAndPaySerializer ));
        } catch (Exception e){
            e.printStackTrace();
            throw new QuantityIsTooLagerException();
        }
    }

    private User getUserByToken(String jwt) {
        return userService.findByEmail(jwtProvider.getUsernameFromToken(jwt)).get();
    }
}
