package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface BrandService {
    <S extends Brand> S save(S entity);

    Optional<Brand> findById(Long id);

    List<Brand> findAll();

    List<Brand> findByName(String name);

    Page<Brand> getAll(Pageable pageable);

    Page<Brand> findByName(String name, Pageable pageable);
}
