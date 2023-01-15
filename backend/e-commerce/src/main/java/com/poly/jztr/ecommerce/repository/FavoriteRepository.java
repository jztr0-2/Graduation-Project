package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Favorite;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findByUser(User user, Pageable pageable);

    Optional<Favorite> findByProductAndUser(Product product, User user);
}
