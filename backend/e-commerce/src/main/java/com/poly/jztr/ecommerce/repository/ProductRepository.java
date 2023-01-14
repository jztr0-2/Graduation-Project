package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Category;
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
    Page<Product> findByNameContains(String name, Pageable pageable);

    Optional<Product> findByName(String name);

    Page<Product> findByNameContainsAndStatus(String name,Boolean status, Pageable pageable);

    Page<Product> findByCategoryAndQuantityIsGreaterThan(Category category, Integer quantity, Pageable pageable);
    Page<Product> findByBrandAndQuantityIsGreaterThan(Brand brand, Integer quantity, Pageable pageable);

    @Query(value = "SELECT products.* FROM order_items join products on " +
            "            products.id = order_items.product_id " +
            "            join categories on products.category_id = categories.id " +
            "            where categories.id = ?1 " +
            "            group by products.id " +
            "            order by (order_items.quantity) desc",
            countQuery = "SELECT count(*) FROM (select order_items join products on " +
                    "            products.id = order_items.product_id " +
                    "            join categories on products.category_id = categories.id " +
                    "            where categories.id = ?1 "+
                    "            group by products.id) as TEMP" ,
            nativeQuery = true )
    Page<Product> findTopSaleByCategory(Long categoryId, Pageable page);

    @Query(value = "select products.name, SUM(order_items.quantity) from orders join order_items on order_items.order_id = orders.id " +
            " join products on order_items.product_id = products.id " +
            " where orders.status = 1 and orders.created_at > ?1 " +
            " group by products.id order by SUM(order_items.quantity) DESC limit 10 ", nativeQuery = true)
    List<Object[]> findStaticsProduct(String time);

    @Query(value = "select products.name, SUM(order_items.quantity) from orders join order_items on order_items.order_id = orders.id " +
            " join products on order_items.product_id = products.id " +
            " where orders.status = 1 and orders.created_at > ?1 " +
            " group by products.id order by SUM(order_items.quantity) ASC limit 10 ", nativeQuery = true)
    List<Object[]> findStaticsProduct(String time, String type);

    @Query(value = "select SUM(order_items.quantity) from orders join order_items on order_items.order_id = orders.id " +
            " join products on order_items.product_id = products.id  where orders.status = 1 and orders.created_at > ?" +
            " order by SUM(order_items.quantity)", nativeQuery = true)
    Optional<Long> totalProductSold(String time);

    @Query(value = "SELECT products.* FROM order_items join products on " +
            "products.id = order_items.product_id group by " +
            "products.id order by (order_items.quantity) desc limit 10", nativeQuery = true)
    List<Product> findTopSale();

    @Query(name = "Product.getProductsByCategoryId")
    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);

    @Query(nativeQuery = true,
    value = "SELECT products.* FROM products join categories on products.category_id = categories.id where categories.id = ?1 ORDER BY RAND() LIMIT ?2")
    List<Product> getRelatedProduct(Long categoryId, Integer limit);

    List<Product> findByBrand(Brand id);

}
