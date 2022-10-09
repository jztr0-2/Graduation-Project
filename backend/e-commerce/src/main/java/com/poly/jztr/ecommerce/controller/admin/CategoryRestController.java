package com.poly.jztr.ecommerce.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.repository.CategoryRespository;

import lombok.val;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/categories")
public class CategoryRestController {

    @Autowired
    CategoryRespository categoryRepo;
    
    @GetMapping("")
    public ResponseEntity<List<Category>> getAll(Model model){
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getOne(Model model, @PathVariable("id") Long id){
        if(!categoryRepo.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryRepo.findById(id).get());
    }

    @PostMapping("")
    public ResponseEntity<Category> post(@Valid @RequestBody Category category, Model mode, Errors errorsl){
        if(categoryRepo.existsById(category.getId())){
            return ResponseEntity.badRequest().build();
        }
        categoryRepo.save(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> put(@Valid @PathVariable("id") Long id, @RequestBody Category category, Errors errors){
        if(!categoryRepo.existsById(category.getId())){
            return ResponseEntity.notFound().build();
        }
        categoryRepo.save(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, Model model){
        if(!categoryRepo.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoryRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
