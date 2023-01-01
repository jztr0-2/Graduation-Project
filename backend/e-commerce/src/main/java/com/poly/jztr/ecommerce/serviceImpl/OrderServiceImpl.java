package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.dto.OrderDto;
import com.poly.jztr.ecommerce.model.*;
import com.poly.jztr.ecommerce.repository.OrderItemRepository;
import com.poly.jztr.ecommerce.repository.OrderRepository;
import com.poly.jztr.ecommerce.service.OrderItemService;
import com.poly.jztr.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import com.poly.jztr.ecommerce.service.PromotionService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    PromotionService promoService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemService orderItemService;


    @Autowired
    PromotionService promotionService;

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
        List<OrderItem> orderItems = entity.getOrderItems().stream().
                map(orderItem -> {
                    orderItem.setOrder(finalEntity);
// minus quantity
                    return orderItem;
                }).collect(Collectors.toList());

        Double total = 0.0;
        for (OrderItem orderItem: orderItems) {
            total = orderItem.getUnitPrice() * orderItem.getQuantity();
            orderItemRepository.save(orderItem);
        }

        entity.setTotal(total);
        return repository.save(entity);

    }

    @Override
    @Transactional()
    public <S extends Order> S save(S entity, String code){
        List<OrderItem> orderItems = entity.getOrderItems();
        entity = repository.save(entity);
        S finalEntity = entity;
        AtomicReference<Double> total = new AtomicReference<>(0.0);
         orderItems.stream().
                map(orderItem -> {
                    orderItem.setOrder(finalEntity);
                    total.set(orderItem.getUnitPrice() * orderItem.getQuantity());
                    orderItemRepository.save(orderItem);
                    return orderItem;
                }).collect(Collectors.toList());
        if(code != null || !((code+"").equalsIgnoreCase("null"))){
            Optional<Promotion> optPro = promoService.findByCode(code);
            boolean check = checkPromo(optPro);
            if(check) {
                if(optPro.get().getAmount() >= total.get()) {
                    entity.setTotal(0.0);
                    entity.setPromotion(optPro.get());
                } else {
                    entity.setTotal(total.get() - optPro.get().getAmount());
                    entity.setPromotion(optPro.get());
                }
            } else {
                throw new TransactionSystemException("promotionInvalid");
            }
        }
        repository.save(entity);
        return null;

    }

    private boolean checkPromo(Optional<Promotion> optPro) {
        if(optPro.isEmpty()) return false;

        Promotion promotion = optPro.get();
        if(promotion.getStatus()){
            if(promotion.getEndDate().isBefore(Instant.now())){
                return false;
            }
            if(promotion.getDeletedAt() != null) return false;
            else{
                promotion.setDeletedAt(Instant.now());
                promoService.save(promotion);
                return true;
            }
        }
        return true;
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

    @Override
    public Page<Order> findByUsername(String firstName, String lastName, Pageable pageable) {
        return  repository.findByUserFirstNameContainsOrUserLastNameContains(firstName, lastName, pageable);
    }


    @Override
    public List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Instant start, Instant end) {
        return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(start, end);
    }

    @Override
    public Page<Order> findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanAndTotalLessThan
            (Integer status, Instant start, Instant end, Double min, Double max, String name,Pageable pageable) {
        return repository.findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContains(
                status, start, end, min, max, name, pageable);
    }

    @Override
    public Page<Order> findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanAndTotalLessThan
            (Instant start, Instant end, Double min, Double max, String name , Pageable pageable) {
        return repository.findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContains
                (start, end, min, max, name,pageable);
    }

    @Override
    public Optional<Order> findByPromotion(String code) {
        return repository.findByPromotionCodeContains(code);
    }

    @Override
    public Order updateStatus(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> findByPhone(String phone) {
        return repository.findByAddressPhone(phone);
    }
}
