package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    <S extends Image> S save(S entity);

    Optional<Image> findById(Long aLong);

    Optional<Image> findByTitle(String title);
}
