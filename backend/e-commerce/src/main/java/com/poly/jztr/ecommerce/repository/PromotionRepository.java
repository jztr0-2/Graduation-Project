package com.poly.jztr.ecommerce.repository;


import com.poly.jztr.ecommerce.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Page<Promotion> findByCodeContains(String code, Pageable pageable);

    Page<Promotion> findByStatus(Integer code, Pageable pageable);

    Page<Promotion> findByCodeContainsAndStatus(String code, Long type, Pageable pageable);
}
