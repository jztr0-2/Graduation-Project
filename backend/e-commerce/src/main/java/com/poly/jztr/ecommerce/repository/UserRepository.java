package com.poly.jztr.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.jztr.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
