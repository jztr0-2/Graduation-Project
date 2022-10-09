package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.CategoryDto;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.repository.CategoryRespository;
import com.poly.jztr.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRespository respository;

    @Override
    public <S extends Category> S save(S entity) {
        return respository.save(entity);
    }
    @Override
    public Optional<Category> findById(Long aLong) {
        return respository.findById(aLong);
    }

    @Override
    public  Category toCategory(CategoryDto dto){
        Category category = new Category();
        category.setName(dto.getName());
        if(dto.getParent_id() != null)
            category.setParent(respository.findById(dto.getParent_id()).get());
        return category;
    }
    @Override
    public boolean existsById(Long aLong) {
        return respository.existsById(aLong);
    }

    @Override
    public List<Category> findAll() {
        return respository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {
        respository.deleteById(aLong);
    }
}
