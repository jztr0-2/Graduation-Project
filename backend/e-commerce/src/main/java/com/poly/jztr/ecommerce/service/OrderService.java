package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface OrderService {
    List<Order> findByDate(Instant start, Instant end);

    List<Long> orderIds(List<Order> orders);

    Order toOrder(OrderDto dto);

    <S extends Order> S save(S entity);

    Page<Order> findByUser(Pageable page, User user);
}
