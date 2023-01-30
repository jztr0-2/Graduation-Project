package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.OrderItemDto;
import com.poly.jztr.ecommerce.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> toOrderItem(List<OrderItemDto> dtos);
    Integer totalSoldProductsByProductId(Long id);
}
