package com.poly.jztr.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.jztr.ecommerce.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findByLastNameContainsOrFirstNameContainsOrEmailContainsOrPhoneContains(String lastName, String firstName
            ,String email, String phone, Pageable pageable);
}
