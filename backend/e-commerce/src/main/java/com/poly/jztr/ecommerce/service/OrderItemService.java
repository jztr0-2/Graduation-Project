package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.OrderItemDto;
import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> toOrderItem(List<OrderItemDto> dtos);
    Integer totalSoldProductsByProductId(Long id);
    Page<Product> getDealOfDay(Pageable pageable);
    Page<Product> getBestSellerProducts(Pageable pageable);
}
