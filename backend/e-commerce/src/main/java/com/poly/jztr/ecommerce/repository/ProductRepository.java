package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameLike(String name);

    Optional<Product> findByName(String name);

    @Query(value = "SELECT products.* FROM jztr.order_items join product_variant on " +
            "product_variant.id = order_items.product_variant_id " +
            "join products on products.id = product_variant.product_id " +
            "join categories on products.category_id = categories.id " +
            "where categories.id = ?1 " +
            "group by products.id " +
            "order by (order_items.quantity) desc ", nativeQuery = true )
    Page<Product> findTopSaleByCategory(Long categoryId, Pageable page);
}
