package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.Favorite;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FavoriteService {
    <S extends Favorite> S save(S entity);

    Optional<Favorite> findById(Long aLong);

    void deleteById(Long aLong);

    Page<Favorite> findByUser(User user, Pageable pageable);

    Optional<Favorite> findByProductAndUser(Product product, User user);
}
