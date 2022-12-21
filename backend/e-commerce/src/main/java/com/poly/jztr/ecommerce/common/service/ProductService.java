package com.poly.jztr.ecommerce.common.service;

import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.ProductStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    <S extends Product> S save(S entity);

    Page<Product> findByNameLike(String name, Pageable pageable);

    Optional<Product> findByName(String name);

    Product toProduct(ProductDto dto);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findTopSaleByCategory(Long categoryId, Pageable page);

    Optional<Product> findById(Long id);

    List<ProductStatic> findStaticsProductTop();

    List<ProductStatic> findStaticsProductsBot();

    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);

    Long getProductSoldThisMonth();

    Page<Product> findByNameContainsAndStatus(String name, Integer status, Pageable pageable);

    List<Product> findTopSale();

    List<Product> findRelatedProduct(Long product_id);
}
