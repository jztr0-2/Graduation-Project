package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Favorite;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.repository.FavoriteRepository;
import com.poly.jztr.ecommerce.service.FavoriteService;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteRepository repository;

    @Override
    public <S extends Favorite> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Favorite> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public Page<Favorite> findByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public Optional<Favorite> findByProductAndUser(Product product, User user) {
        return repository.findByProductAndUser(product, user);
    }
}

