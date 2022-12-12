package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.ProductImage;

public interface ProductImageService {
    <S extends ProductImage> S save(S entity);
}
