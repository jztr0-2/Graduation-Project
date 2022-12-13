package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Address;
import com.poly.jztr.ecommerce.repository.AddressRepository;
import com.poly.jztr.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository repository;

    @Override
    public <S extends Address> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Address> findById(Long aLong) {
        return repository.findById(aLong);
    }
    @Override
    public <S extends Address> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }
    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Address> finByPhoneContains(String phone) {
        return repository.findByPhoneContains(phone);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
