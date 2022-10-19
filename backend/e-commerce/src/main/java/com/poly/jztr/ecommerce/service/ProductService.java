package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    <S extends Product> S save(S entity);

    List<Product> findByNameLike(String name);

    Product toProduct(ProductDto dto);
}
