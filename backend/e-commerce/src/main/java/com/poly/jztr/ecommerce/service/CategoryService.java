package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.CategoryDto;
import com.poly.jztr.ecommerce.model.Category;

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
}
