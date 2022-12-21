package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Promotion;
import com.poly.jztr.ecommerce.repository.PromotionRepository;
import com.poly.jztr.ecommerce.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionRepository respository;

    @Override
    public <S extends Promotion> S save(S entity) {
        return respository.save(entity);
    }
    @Override
    public Optional<Promotion> findById(Long id) {
        return respository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return respository.existsById(id);
    }

    @Override
    public List<Promotion> findAll() {
        return respository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        respository.deleteById(id);
    }

    @Override
    public Page<Promotion> findByCodeLContains(String code, Pageable pageable) {
        return respository.findByCodeContains(code, pageable);
    }

    @Override
    public Page<Promotion> findByStatus(Integer code, Pageable pageable) {
        return respository.findByStatus(code, pageable);
    }

    @Override
    public Page<Promotion> findByCodeContainsAndStatus(String code, Integer status, Pageable pageable) {
        return respository.findByCodeContainsAndStatus(code, Long.valueOf(status), pageable);
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return respository.findByCodeContains(code);
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return respository.findByCodeContains(code);
    }

}
