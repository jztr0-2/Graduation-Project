package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.Address;
import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.repository.OrderItemRepository;
import com.poly.jztr.ecommerce.repository.OrderRepository;
import com.poly.jztr.ecommerce.service.OrderItemService;
import com.poly.jztr.ecommerce.service.OrderService;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductVariantService productVariantService;

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
    @Transactional()
    public <S extends Order> S save(S entity) {
        entity = repository.save(entity);
        S finalEntity = entity;
        List<OrderItem> orderItems = entity.getOrderItems().stream().
                map(orderItem -> {
                 orderItem.setOrder(finalEntity);
                 productVariantService.minusQuantity(orderItem.getProductVariant().getId(),orderItem.getQuantity());
                 return orderItem;
                }).collect(Collectors.toList());
        orderItemRepository.saveAll(orderItems);
        return null;
    }

    @Override
    public Page<Order> findByUser(Pageable page, User user){
        return repository.findByUser(user, page);
    }

    @Override
    public Page<Order> findByStatusIs(Integer status, Pageable pageable) {
        return repository.findByStatusIs(status, pageable);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Double totalRevenueThisMonth(){
        String time = LocalDate.now().withDayOfMonth(1) + "";
        Optional<Double> optional = repository.totalRevenueInThisMonth(time);
        if(optional.isPresent()) return optional.get();
        return 0D;
    }

    @Override
    public List<Object[]> totalRevenuePerMonth(){
        return repository.totalRevenuePerMonth();
    }
}
