package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.model.User;

public interface UserService {

    <S extends User> S save(S entity);
}
