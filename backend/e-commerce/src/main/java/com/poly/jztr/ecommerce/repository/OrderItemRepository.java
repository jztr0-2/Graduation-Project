package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderIdIn(List<Long> ids);
    Integer totalSoldProductsByProductId(Long id);
    Page<Product> getDealOfDay(Pageable pageable);
    Page<Product> getBestSellerProducts(Pageable pageable);
}
