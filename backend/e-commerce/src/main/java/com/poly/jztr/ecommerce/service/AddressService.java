package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    <S extends Address> S save(S entity);

    Optional<Address> findById(Long aLong);

    <S extends Address> List<S> saveAll(Iterable<S> entities);

    List<Address> findAll();

    List<Address> finByPhoneContains(String phone);

    void deleteById(Long aLong);
}
