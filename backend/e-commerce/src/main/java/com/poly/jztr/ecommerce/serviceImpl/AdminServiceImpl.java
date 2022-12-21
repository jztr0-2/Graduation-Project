package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Admin;
import com.poly.jztr.ecommerce.repository.AdminRepository;
import com.poly.jztr.ecommerce.common.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository repository;

    @Override
    public Optional<Admin> findByLoginName(String name){
        return repository.findByLoginName(name);
    }

    @Override
    public <S extends Admin> S save(S entity) {
        return repository.save(entity);
    }
}
