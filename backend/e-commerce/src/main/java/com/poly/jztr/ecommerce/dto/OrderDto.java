package com.poly.jztr.ecommerce.dto;

import com.poly.jztr.ecommerce.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String description;
    private Long addressId;
    private Long promoId;
    private List<OrderItemDto> items;
    private Integer status;
}
