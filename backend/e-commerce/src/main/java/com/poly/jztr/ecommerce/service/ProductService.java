package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    <S extends Product> S save(S entity);

    List<Product> findByNameLike(String name);

    Optional<Product> findByName(String name);

    Product toProduct(ProductDto dto);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findTopSaleByCategory(Long categoryId, Pageable page);

    Optional<Product> findById(Long id);
}
