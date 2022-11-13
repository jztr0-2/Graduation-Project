package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.CategoryDto;
import com.poly.jztr.ecommerce.dto.PromotionDto;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Promotion;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.PromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice("admin/promotion")
@CrossOrigin("localhost:3000")
@RequestMapping("api/v1/admin/promotion")
public class PromotionController {

    @Autowired
    PromotionService service;
    
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAll(Model model){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOne(@PathVariable("id") Long id){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Data not found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully", service.findById(id).get()));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> post(@RequestBody @Validated PromotionDto promotionDto) throws IllegalAccessException, InvocationTargetException{
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDto, promotion);
        promotion.setType(1L);
        return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Crated promotion successfully", service.save(promotion)));
    
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> put(@Valid @PathVariable("id") Long id, @RequestBody PromotionDto promotionDto){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"", null));
        }
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDto, promotion);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"", service.save(promotion)));
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
