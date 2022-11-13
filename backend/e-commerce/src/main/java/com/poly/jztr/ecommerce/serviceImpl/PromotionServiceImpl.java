package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Promotion;
import com.poly.jztr.ecommerce.repository.PromotionRepository;
import com.poly.jztr.ecommerce.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
