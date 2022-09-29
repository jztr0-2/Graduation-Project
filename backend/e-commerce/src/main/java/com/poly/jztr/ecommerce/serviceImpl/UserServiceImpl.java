package com.poly.jztr.ecommerce.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public <S extends User> S save(S entity) {
        if(entity.getId() == null)  entity.setCreatedAt(Instant.now());

        entity.setUpdatedAt(Instant.now());
        return repository.save(entity);
    }

    @Override
    public UserDto getJson(String user, MultipartFile file){
        UserDto dto = new UserDto();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            dto = objectMapper.readValue(user,UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }
}
