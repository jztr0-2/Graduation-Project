package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
