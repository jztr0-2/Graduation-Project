package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByTitle(String title);
}
