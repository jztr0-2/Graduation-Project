package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Address;
import com.poly.jztr.ecommerce.repository.AddressRepository;
import com.poly.jztr.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository repository;

    @Override
    public <S extends Address> S save(S entity) {
        return repository.save(entity);
    }
}
