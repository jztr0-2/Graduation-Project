package com.poly.jztr.ecommerce.controller.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.Valid;

import com.poly.jztr.ecommerce.service.CategoryService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.CategoryDto;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.repository.CategoryRespository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@RestControllerAdvice("admin/categories") 
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/admin/categories")
public class CategoriesController {

    @Autowired
    CategoryService service;
    
    @GetMapping("")
    public ResponseEntity<List<Category>> getAll(Model model){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getOne(Model model, @PathVariable("id") Long id){
        if(!service.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> post(@RequestBody @Validated CategoryDto category) throws IllegalAccessException, InvocationTargetException{
        Category cate = service.toCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", service.save(cate)));
    
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> put(@Valid @PathVariable("id") Long id, @RequestBody Category category, Errors errors){
        if(!service.existsById(category.getId())){
            return ResponseEntity.notFound().build();
        }
        service.save(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, Model model){
        if(!service.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
