package com.poly.jztr.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.jztr.ecommerce.model.Category;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);
    @Query(value =
            "SELECT  categories.* FROM order_items join product_variant on " +
                    "product_variant.id = order_items.product_variant_id join products " +
                    "on products.id = product_variant.product_id join categories on" +
                    " products.category_id = categories.id where delete_at is null group by " +
                    "category_id order by sum(order_items.quantity) desc limit 1", nativeQuery = true
    )
    Category findTopSaleCategory();
    @Query(name = "Category.findPageCategory")
    Page<Category> findPageCategory(Pageable pageable);

    Page<Category> findByNameContains(String name, Pageable pageable);
}
