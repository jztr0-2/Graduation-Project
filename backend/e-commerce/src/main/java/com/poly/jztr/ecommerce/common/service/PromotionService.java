package com.poly.jztr.ecommerce.common.service;


import com.poly.jztr.ecommerce.model.Promotion;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PromotionService {


    <S extends Promotion> S save(S entity);

    Optional<Promotion> findById(Long id);

    //    @Override
    //    public  Category toCategory(CategoryDto dto){
    //        Category category = new Category();
    //        category.setName(dto.getName());
    //        if(dto.getParent_id() != null)
    //            category.setParent(respository.findById(dto.getParent_id()).get());
    //        return category;
    //    }
    boolean existsById(Long id);

    List<Promotion> findAll();

    void deleteById(Long id);

    Page<Promotion> findByCodeLContains(String code, Pageable pageable);


    Page<Promotion> findByStatus(Integer code, Pageable pageable);

    Page<Promotion> findByCodeContainsAndStatus(String code, Integer status, Pageable pageable);
}
