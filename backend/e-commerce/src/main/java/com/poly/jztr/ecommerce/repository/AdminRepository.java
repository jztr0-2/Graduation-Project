package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByLoginName(String loginName);
}
