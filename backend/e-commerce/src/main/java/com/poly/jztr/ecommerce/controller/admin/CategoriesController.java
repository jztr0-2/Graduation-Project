package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.CategoryDto;
import com.poly.jztr.ecommerce.model.Category;

import java.time.Instant;
import java.util.Optional;

@RestControllerAdvice("admin/categories") 
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/categories")
public class CategoriesController {

    @Autowired
    CategoryService service;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByName(@RequestParam(defaultValue = "", name = "name") String name,
                                                     @RequestParam(defaultValue = "1", name = "page") Integer page,
                                                     @RequestParam(defaultValue = "10", name = "perPage") Integer perPage) {
        PageableSerializer data = null;
        Pageable pageable = CustomPageable.getPage(page, perPage);
        if("".equals(name)) {
            data = new PageableSerializer(service.findAll(pageable));
        } else {
            data = new PageableSerializer(service.findByNameContains(name,pageable));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data status successfully",
                        data));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOne(@PathVariable("id") Long id){
//        if(!service.existsById(id)){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Category", null));
//        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Found Category", service.findById(id).get()));
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
    public ResponseEntity<ResponseObject> put(@PathVariable("id") Long id,@RequestBody @Validated CategoryDto category) throws DuplicateEntryException {
        Optional<Category> optionalCategory = service.findById(id);
        if(optionalCategory.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Category not found", null));
        }
        Category cate = optionalCategory.get();
        Optional<Category> categoryOptionaName = service.findByName(category.getName());
        if (categoryOptionaName.isPresent() && cate.getId() == categoryOptionaName.get().getId()){
            throw new DuplicateEntryException("Name", "Category is exists");
        }

        cate.setName(category.getName());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update Category success", service.save(cate)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id, Model model){
        Optional<Category> cate = service.findById(id);
        if(cate.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"", null));
        }
        Category category = cate.get();
        service.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", null));
    }

}
