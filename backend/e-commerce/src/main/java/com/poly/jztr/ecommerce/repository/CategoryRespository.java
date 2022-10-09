package com.poly.jztr.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.stereotype.Repository;

import com.poly.jztr.ecommerce.model.Category;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Long>{
    
}
