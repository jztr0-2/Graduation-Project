package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.repository.BrandRepository;
import com.poly.jztr.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository repository;

    @Override
    public <S extends Brand> S save(S entity) {
        Brand b =  repository.save(entity);
        return (S) b;
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Brand> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Brand> findByName(String name) {
        return repository.findByNameContains(name);
    }


    @Override
    public Page<Brand> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Override
    public Page<Brand> findByName(String name, Pageable pageable) {
        return repository.findByNameContains(name, pageable);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
