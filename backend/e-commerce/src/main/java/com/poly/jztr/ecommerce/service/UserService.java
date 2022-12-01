package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.data.domain.Example;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {

    <S extends User> S save(S entity);

    UserDto getJson(String user, MultipartFile file);

    Optional<User> findById(Long aLong);

    Optional<User> findByEmail(String email);

    long count();
}
