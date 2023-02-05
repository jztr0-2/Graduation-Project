package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.dto.OrderItemDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findByDate(Instant start, Instant end);

    List<Long> orderIds(List<Order> orders);

    Order toOrder(OrderDto dto);

    <S extends Order> S save(S entity);

    Page<Order> findByUser(Pageable page, User user);

    Page<Order> findByStatusIs(Integer status, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    Optional<Order> findById(Long id);

    long count();

    Double totalRevenueThisMonth();

    List<Object[]> totalRevenuePerMonth();

    Page<Order> findByUsername(String first, String lastName, Pageable pageable);


    List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Instant start, Instant end);

    Page<Order> findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanAndTotalLessThan
            (Integer status, Instant start, Instant end, Double min,Double max, String name, Pageable pageable);
    Page<Order> findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanAndTotalLessThan
            (Instant start, Instant end, Double min, Double max,String name, Pageable pageable);

    @Transactional()
    <S extends Order> S save(S entity, String code);


    Page<Order> findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContainsAndIdContains(Integer status, Instant start, Instant end, Double min, Double max, String name, String email, String phone, Pageable pageable);

    Page<Order> findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContainsAndUserEmailContainsAndUserPhoneContainsAndIdContains(Instant start, Instant end, Double min, Double max, String name, String email, String phone, Pageable pageable);

    Optional<Order> findByPromotion(String code);

    Order updateStatus(Order order);

    List<Order> findByPhone(String phone);

    @Async
    void checkPayment(Long id) throws InterruptedException;

    void updateQuantity(List<OrderItemDto> dtos);
}
