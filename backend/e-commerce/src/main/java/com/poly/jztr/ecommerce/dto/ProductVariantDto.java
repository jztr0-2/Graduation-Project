package com.poly.jztr.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductVariantDto {
    private Integer quantity;
    private Double unitPrice;
    private String description;
    private Long productId;
    private Long imageId;
    private Long id;
}
