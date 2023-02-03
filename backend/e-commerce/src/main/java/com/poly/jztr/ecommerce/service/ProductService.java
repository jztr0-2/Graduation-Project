package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.ProductStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    <S extends Product> S save(S entity);

    Page<Product> findByNameLike(String name, Pageable pageable);

    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findTopSaleByCategory(Long categoryId, Pageable page);

    Optional<Product> findById(Long id);

    List<ProductStatic> findStaticsProductTop();

    List<ProductStatic> findStaticsProductsBot();

    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);

    Long getProductSoldThisMonth();

    Page<Product> findByNameContainsAndStatus(String name, Boolean status, Pageable pageable);

    List<Product> findTopSale();

    List<Product> findRelatedProduct(Long product_id);

    List<Product> findByBrandId(Brand id);
    Page<Product> findByCategoryAndQuantityIsGreaterThan(Category category, Integer quantity, Pageable pageable);

    Page<Product> findByBrandAndQuantityIsGreaterThan(Brand brand, Integer quantity, Pageable page);
    int countProducts();
    Page<Product> searchProducts(String searchName, Pageable pageable);
    List<Product> randomProducts(Long id, int limit);
    Page<Product> getNewProducts(Pageable pageable);
    Page<Product> getCurrentCreatedProducts(Pageable pageable);
}
