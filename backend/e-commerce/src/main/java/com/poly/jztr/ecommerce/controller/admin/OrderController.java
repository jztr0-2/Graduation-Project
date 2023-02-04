package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.DateHelper;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.serializer.OrderStatics;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.serializer.ProductStatic;
import com.poly.jztr.ecommerce.serializer.RecentOrderSerializer;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.SendSMSService;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice("admin/orders")
@CrossOrigin(value = {"http://localhost:3000", "http://localhost:4000"})
@RequestMapping("api/v1/admin/orders")
public class OrderController {
    @Autowired
    OrderService service;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    SendSMSService sendSMSService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByStatus(@RequestParam(defaultValue = "-1") Integer status,
                                                       @RequestParam(defaultValue = "") String email,
                                                       @RequestParam(defaultValue = "") String phone,
                                                       @RequestParam(defaultValue = "-1") Long id,
                                                       @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer perPage,
                                                       @RequestParam(defaultValue = "2020-12-28") String startDate,
                                                       @RequestParam(defaultValue = "2050-12-30") String endDate,
                                                       @RequestParam(defaultValue = "0") Double min,
                                                       @RequestParam(defaultValue = "9999999999999") Double max,
                                                       @RequestParam(defaultValue = "") String name){
        Pageable pageable = CustomPageable.getPage(page, perPage, "createdAt", Constant.SORT_DESC);
//        Instant start = Instant.EPOCH.plus(startDate, ChronoUnit.SECONDS);
//        Instant end = Instant.EPOCH.plus(endDate, ChronoUnit.SECONDS);
        Instant start = DateHelper.toDate(startDate,"yyyy-MM-dd");
        Instant end = DateHelper.toDate(endDate,"yyyy-MM-dd");
        System.out.println(start + " "+ end);

        if(id != -1) {
            Optional<Order> opt = service.findById(id);
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Order not found", null));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully", opt.get()));
        }

        if(status == -1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
                            service.findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContainsAndIdContains
                                    (start,end,min,max,name,email,phone,pageable)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
                        new PageableSerializer(
                                service.findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContainsAndIdContains
                                        (status,start,end,min,max,name,email,phone,pageable))));
    }

    @GetMapping("username")
    public ResponseEntity<ResponseObject> finByUsername(@RequestParam(defaultValue = "") String name,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer perPage){
        Pageable pageable = CustomPageable.getPage(page, perPage, "createdAt", Constant.SORT_DESC);
        if("".equalsIgnoreCase(name) ) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
                            service.findAll(pageable)));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data username successfully",
                            service.findByUsername(name, name, pageable)));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Find Order Success", service.findById(id).get()));
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
        if(order.getStatus() != dto.getStatus()){
            String stts = "";
            if(dto.getStatus() == Constant.ORDER_STATUS_SUCCESS ){
                stts = "Success";
            }else {
                stts = "Cancel";
            }
            String phone = "+" + order.getAddress().getPhone();
            Date date = new Date(order.getCreatedAt().getEpochSecond());
            sendSMSService.send(phone,"Your orders in: "
                    + DateHelper.toStrings(date, "dd-MM-yyyy") + "with total: "+ order.getTotal() +
                    "has been change status to" + "");
        }
        order.setStatus(dto.getStatus());
        order = service.updateStatus(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update order successfully", order));
    }

    @GetMapping("statistical")
    public ResponseEntity<ResponseObject> getStatistical(){
        long userCount = userService.count();
        long productSold = productService.getProductSoldThisMonth();
        long orderCount = service.count();
        double totalRevenue = service.totalRevenueThisMonth();
        List<ProductStatic> top = productService.findStaticsProductTop();
        List<ProductStatic> bottom = productService.findStaticsProductsBot();
        List<Object []> totalRevenuePerMonth = service.totalRevenuePerMonth();
        OrderStatics orderStatics = new OrderStatics(userCount,productSold,orderCount,totalRevenue,totalRevenuePerMonth,top,bottom);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get order statistical successfully",
                        orderStatics));
    }

    @GetMapping("recent-order")
    public ResponseEntity<ResponseObject> getRecentOrder(){
        Pageable pageable = CustomPageable.getPage(1, 20, "createdAt", Constant.SORT_DESC);
        Page<Order> orderPage = service.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get order statistical successfully",
                        new RecentOrderSerializer(orderPage.getContent())));
    }

}
