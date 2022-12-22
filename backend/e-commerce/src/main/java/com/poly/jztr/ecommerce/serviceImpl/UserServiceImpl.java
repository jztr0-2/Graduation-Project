package com.poly.jztr.ecommerce.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public <S extends User> S save(S entity) {
        if (entity.getId() == null) entity.setCreatedAt(Instant.now());

        entity.setUpdatedAt(Instant.now());
        return repository.save(entity);
    }

    @Override
    public UserDto getJson(String user, MultipartFile file) {
        UserDto dto = new UserDto();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            dto = objectMapper.readValue(user, UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public long count() {
        return repository.count();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<User> findByProperties(Pageable pageable, String name, String email, String phone) {
        return repository.findByLastNameContainsOrFirstNameContainsOrEmailContainsOrPhoneContains(name, name,email, phone,pageable);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public User findByEmailOrPhone(String usernameFromToken) {
        return repository.findByEmailOrPhone(usernameFromToken).get();
    }
}
