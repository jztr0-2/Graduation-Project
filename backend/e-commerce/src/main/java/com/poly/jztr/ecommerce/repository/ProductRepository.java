package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameLike(String name, Pageable pageable);

    Optional<Product> findByName(String name);

    @Query(value = "SELECT products.* FROM jztr.order_items join product_variant on " +
            "product_variant.id = order_items.product_variant_id " +
            "join products on products.id = product_variant.product_id " +
            "join categories on products.category_id = categories.id " +
            "where categories.id = ?1 " +
            "group by products.id " +
            "order by (order_items.quantity) desc ",
            countQuery = "SELECT count(*) FROM (select products.* from jztr.order_items join product_variant on " +
                    " product_variant.id = order_items.product_variant_id " +
                    " join products on products.id = product_variant.product_id " +
                    " join categories on products.category_id = categories.id " +
                    " where categories.id = ?1 " +
                    " group by products.id) AS TEMP" ,
            nativeQuery = true )
    Page<Product> findTopSaleByCategory(Long categoryId, Pageable page);

    @Query(value = "select products.name, SUM(order_items.quantity) from orders join order_items on order_items.order_id = orders.id " +
            " join product_variant on order_items.product_variant_id = product_variant.id " +
            " join products on product_variant.product_id = products.id where orders.status = 1 and orders.created_at > ?1 " +
            " group by products.id order by SUM(order_items.quantity) DESC limit 10 ", nativeQuery = true)
    List<Object[]> findStaticsProduct(String time);

    @Query(value = "select products.name, SUM(order_items.quantity) from orders join order_items on order_items.order_id = orders.id " +
            " join product_variant on order_items.product_variant_id = product_variant.id " +
            " join products on product_variant.product_id = products.id where orders.status = 1 and orders.created_at > ?1 " +
            " group by products.id order by SUM(order_items.quantity) ASC limit 10 ", nativeQuery = true)
    List<Object[]> findStaticsProduct(String time, String type);

    @Query(value = "select SUM(order_items.quantity) from orders join order_items on order_items.order_id = orders.id " +
            " join product_variant on order_items.product_variant_id = product_variant.id " +
            " join products on product_variant.product_id = products.id where orders.status = 1 " +
            "and orders.created_at > ?" +
            "  order by SUM(order_items.quantity)", nativeQuery = true)
    Optional<Long> totalProductSold(String time);

    @Query(name = "Product.getProductsByCategoryId")
    Page<Product> getProductsByCategoryId(Long categoryId, Pageable page);
}
