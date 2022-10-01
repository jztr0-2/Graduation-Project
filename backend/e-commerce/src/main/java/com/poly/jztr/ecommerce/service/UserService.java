package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    <S extends User> S save(S entity);

    UserDto getJson(String user, MultipartFile file);
}
