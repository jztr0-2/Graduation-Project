package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Promotion;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByNameContains(String name);

    @Override
    <S extends Brand> Page<S> findAll(Example<S> example, Pageable pageable);

    Page<Brand> findByNameContains(String name, Pageable pageable);

    @Query(value = "select distinct  products.brand_id from products", nativeQuery = true)
    List<Long> findBrandIds();

    List<Brand> findByIdIn(List<Long> ids);
}