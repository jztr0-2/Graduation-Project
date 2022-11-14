package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.OrderItemDto;
import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Override
    public List<OrderItem> toOrderItem(List<OrderItemDto> dtos){
       return dtos.stream().map(dto ->{
           OrderItem orderItem = new OrderItem();
           orderItem.setQuantity(dto.getQuantity());
           orderItem.setProductVariant(new ProductVariant(dto.getProductVariantId()));
           orderItem.setUnitPrice(dto.getUnitPrice());
           return  orderItem;
       }) .collect(Collectors.toList());
    }
}
