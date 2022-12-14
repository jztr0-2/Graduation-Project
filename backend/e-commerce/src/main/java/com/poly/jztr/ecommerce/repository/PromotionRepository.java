package com.poly.jztr.ecommerce.repository;


import com.poly.jztr.ecommerce.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Page<Promotion> findByCodeContains(String code, Pageable pageable);

    Page<Promotion> findByStatus(Boolean code, Pageable pageable);

    Page<Promotion> findByCodeContainsAndStatus(String code, Boolean status, Pageable pageable);

    Optional<Promotion> findByCode(String code);
}
