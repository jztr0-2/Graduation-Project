package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.PromotionDto;
import com.poly.jztr.ecommerce.model.Promotion;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.PromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice("admin/promotion")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/promotion")
public class PromotionController {

    @Autowired
    PromotionService service;
    
//    @GetMapping("")
//    public ResponseEntity<ResponseObject> getAll(Model model){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get All Promotion Success", service.findAll()));
//    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByCode(@RequestParam(defaultValue = "-1") Integer type,
                                                     @RequestParam(defaultValue = "") String code,
                                                     @RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer perPage){
        PageableSerializer data = null;
        Pageable pageable = CustomPageable.getPage(page, perPage);
        if(type == -1) data = new PageableSerializer(service.findByCodeLContains(code, pageable));
        else data = new PageableSerializer(service.findByCodeContainsAndType(code,type, pageable));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
                        data));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) throws TypeMismatchException {
//        if(service.findById(id).isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Promotion", null));
//        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Find Promotion",
                        service.findById(id)));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> post(@RequestBody @Validated PromotionDto promotionDto) throws IllegalAccessException, InvocationTargetException{
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDto, promotion);
        promotion.setType(1);
        return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Crated promotion successfully", service.save(promotion)));
    
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> put(@Valid @PathVariable("id") Long id, @RequestBody PromotionDto promotionDto){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Promotion", null));
        }
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDto, promotion);
        promotion.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update Promotion Success", service.save(promotion)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Promotion", null));
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Delete Promotion Success", null));
    }
}
