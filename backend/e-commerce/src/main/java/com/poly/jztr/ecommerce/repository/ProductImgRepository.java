package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImage, Long> {
    
}
