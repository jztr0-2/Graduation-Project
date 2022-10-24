package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Address;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.repository.OrderItemRepository;
import com.poly.jztr.ecommerce.repository.OrderRepository;
import com.poly.jztr.ecommerce.service.OrderItemService;
import com.poly.jztr.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public List<Order> findByDate(Instant start, Instant end) {
        if (start == null) start = Instant.parse("2021-02-09");
        if (end == null) end = Instant.now();
        return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(start, end);
    }

    @Override
    public List<Long> orderIds(List<Order> orders) {
        return orders.stream().map(Order::getId).collect(Collectors.toList());
    }

    @Override
    public Order toOrder(OrderDto dto) {
        Order order = new Order();
        order.setAddress(new Address(dto.getAddressId()));
        order.setDescription(dto.getDescription());
        order.setStatus(Constant.ORDER_STATUS_PENDING);
        order.setOrderItems(orderItemService.toOrderItem(dto.getItems()));
        return order;
    }

    @Override
    public <S extends Order> S save(S entity) {
        entity = repository.save(entity);
        S finalEntity = entity;
        System.out.println(finalEntity.getId());
        List<OrderItem> orderItems = entity.getOrderItems().stream().
                map(orderItem -> {
                 orderItem.setOrder(finalEntity);
                 return orderItem;
                }).collect(Collectors.toList());
        orderItemRepository.saveAll(orderItems);
        return null;
    }
}
