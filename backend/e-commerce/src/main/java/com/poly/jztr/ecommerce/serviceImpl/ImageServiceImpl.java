package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Image;
import com.poly.jztr.ecommerce.repository.ImageRepository;
import com.poly.jztr.ecommerce.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository repository;

    @Override
    public <S extends Image> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Image> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Image> findByProductId(Long productId) {
        return repository.findByProductId(productId);
    }
}
