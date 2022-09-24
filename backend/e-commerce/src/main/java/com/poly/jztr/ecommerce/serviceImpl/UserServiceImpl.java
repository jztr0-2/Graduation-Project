package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.repository.UserRepository;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public <S extends User> S save(S entity) {
        if(entity.getId() != null)  entity.setCreatedAt(Instant.now());

        entity.setUpdatedAt(Instant.now());
        return repository.save(entity);
    }
}
