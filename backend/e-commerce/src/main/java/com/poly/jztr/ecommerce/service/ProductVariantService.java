package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.ProductVariantDto;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;

import java.util.List;

public interface ProductVariantService {
    List<ProductVariant> toProductVariant(List<Product> products);

    List<ProductVariant> toProductVariantFromDto(List<ProductVariantDto> list);

    <S extends ProductVariant> S save(S entity);

    void minusQuantity(Long id, Integer quantity);
}
