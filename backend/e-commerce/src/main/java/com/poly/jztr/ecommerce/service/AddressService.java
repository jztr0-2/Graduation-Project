package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.Address;

public interface AddressService {
    <S extends Address> S save(S entity);
}
