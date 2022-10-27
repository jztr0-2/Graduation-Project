package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameLike(String name);

    Optional<Product> findByName(String name);

//    @Modifying
//    @Query(value = "")
//    Page<Product> findBySale(List<Long> ids);
}
