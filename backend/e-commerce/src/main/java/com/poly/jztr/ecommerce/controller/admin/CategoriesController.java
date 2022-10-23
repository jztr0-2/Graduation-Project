package com.poly.jztr.ecommerce.controller.admin;


import javax.validation.Valid;

import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

import java.util.Optional;

@RestControllerAdvice("admin/categories") 
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/categories")
public class CategoriesController {

    @Autowired
    CategoryService service;



    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOne(Model model, @PathVariable("id") Long id){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", service.findById(id).get()));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> post(@RequestBody @Validated CategoryDto category) throws DuplicateEntryException {
        if(category.getId() != null) {
            Optional<Category> categoryDb = service.findById(category.getId());
            if(categoryDb.isPresent()) {
                Category newCate = categoryDb.get();
                newCate.setName(category.getName());
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update Category Success", service.save(newCate)));
            }
        }
        if(service.findByName(category.getName()).isPresent()) {
            throw new DuplicateEntryException("Name", "Category is exists");
        }

        Category cate = service.toCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", service.save(cate)));
    
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> put(@Valid @PathVariable("id") Long id, @RequestBody CategoryDto category){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"", null));
        }
        Category cate = service.toCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", service.save(cate)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id, Model model){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"", null));
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", null));
    }
}
