package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;

import java.util.List;

public interface ProductVariantService {
    List<ProductVariant> toProductVariant(List<Product> products);

    <S extends ProductVariant> S save(S entity);
}
