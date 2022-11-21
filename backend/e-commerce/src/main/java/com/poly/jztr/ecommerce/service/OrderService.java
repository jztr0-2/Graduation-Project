package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Order;

import java.time.Instant;
import java.util.List;

public interface OrderService {
    List<Order> findByDate(Instant start, Instant end);

    List<Long> orderIds(List<Order> orders);

    Order toOrder(OrderDto dto);

    <S extends Order> S save(S entity);
}
