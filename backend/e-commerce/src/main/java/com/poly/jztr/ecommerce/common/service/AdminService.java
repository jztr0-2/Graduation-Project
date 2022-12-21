package com.poly.jztr.ecommerce.common.service;

import com.poly.jztr.ecommerce.model.Admin;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> findByLoginName(String name);

    <S extends Admin> S save(S entity);
}
