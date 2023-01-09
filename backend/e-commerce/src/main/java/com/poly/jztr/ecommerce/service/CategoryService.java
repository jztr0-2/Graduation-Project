package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.CategoryDto;
import com.poly.jztr.ecommerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    <S extends Category> S save(S entity);

    Optional<Category> findById(Long aLong);

    Category toCategory(CategoryDto dto);

    boolean existsById(Long aLong);

    List<Category> findAll();

    void deleteById(Long aLong);

    Optional<Category> findByName(String name);


    Category findTopSaleCategory();
    Page<Category> findPageCategory(Pageable pageable);

    Page<Category> findByNameContains(String name, Pageable pageable);

    Page<Category> findAll(Pageable pageable);
}
