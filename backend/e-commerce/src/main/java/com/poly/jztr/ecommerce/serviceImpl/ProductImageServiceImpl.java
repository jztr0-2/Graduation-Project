package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.ProductImage;
import com.poly.jztr.ecommerce.repository.ProductImgRepository;
import com.poly.jztr.ecommerce.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImgRepository repository;

    @Override
    public <S extends ProductImage> S save(S entity) {
        return repository.save(entity);
    }


}
