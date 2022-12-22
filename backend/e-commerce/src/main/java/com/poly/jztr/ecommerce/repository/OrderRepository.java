package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Instant start, Instant end);

    Page<Order> findByUser(User user, Pageable page);

    Page<Order> findByStatusIs(Integer status, Pageable pageable);

    Page<Order> findByStatusAndCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContains(Integer status,
                Instant start,Instant end, Double min, Double max, String name,Pageable pageable);

    Page<Order> findByCreatedAtAfterAndCreatedAtBeforeAndTotalGreaterThanEqualAndTotalLessThanEqualAndUserFirstNameContains
            (Instant start,Instant end, Double min, Double max,String name, Pageable pageable);

    @Query(value = "select SUM(order_items.unit_price * order_items.quantity) from orders join order_items on order_items.order_id = orders.id" +
            " join product_variant on order_items.product_variant_id = product_variant.id " +
            " join products on product_variant.product_id = products.id  where orders.status = 1 and orders.created_at > ?",
    nativeQuery = true)
    Optional<Double> totalRevenueInThisMonth(String time);

    @Query(value =
            "select SUM(order_items.unit_price * order_items.quantity), CONVERT(date_format(orders.created_at,\"%m-%Y\") ,char(7)) " +
                    " from orders join order_items on order_items.order_id = orders.id " +
                    " join product_variant on order_items.product_variant_id = product_variant.id " +
                    " join products on product_variant.product_id = products.id  where orders.status = 1 " +
                    " and orders.created_at > date_sub(now(),interval 1 YEAR) " +
                    " group by (MONTH(orders.created_at)),  YEAR(orders.created_at)",
            nativeQuery = true)
    List<Object[]> totalRevenuePerMonth();

    Page<Order> findByUserFirstNameContainsOrUserLastNameContains(String firstName, String lastName, Pageable pageable);

    Optional<Order> findByPromotionCodeContains(String code);

    List<Order> findByAddressPhone(String phone);
}
