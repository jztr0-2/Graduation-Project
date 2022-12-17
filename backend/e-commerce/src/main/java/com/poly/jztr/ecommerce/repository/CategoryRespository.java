package com.poly.jztr.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.jztr.ecommerce.model.Category;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);
    List<Category> findByDeleteAt(Instant deleted);

    Optional<Category> findByIdAndDeleteAt(Long aLong, Instant deleted);
}
