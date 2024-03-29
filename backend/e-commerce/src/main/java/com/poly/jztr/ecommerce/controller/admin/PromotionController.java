package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.DateHelper;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.PromotionDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
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
import java.time.Instant;
import java.util.Optional;

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

//    @GetMapping("")
//    public ResponseEntity<ResponseObject> findByCode(@RequestParam(defaultValue = "-1") Integer status,
//                                                     @RequestParam(defaultValue = "") String code,
//                                                     @RequestParam(defaultValue = "1") Integer page,
//                                                     @RequestParam(defaultValue = "10") Integer perPage){
//        PageableSerializer data = null;
//        Pageable pageable = CustomPageable.getPage(page, perPage);
//        if(status == -1) data = new PageableSerializer(service.findByCodeLContains(code, pageable));
//        else data = new PageableSerializer(service.findByCodeContainsAndStatus(code,status, pageable));
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data successfully",
//                        data));
//    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByStatus(@RequestParam Optional<Boolean> status,
                                                       @RequestParam(defaultValue = "") String code,
                                                       @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer perPage){
        PageableSerializer data = null;
        Pageable pageable = CustomPageable.getPage(page, perPage);
        System.err.println(status.isEmpty());
        if(status.isEmpty()) data = new PageableSerializer(service.findByCodeLContains(code, pageable));
        else data = new PageableSerializer(service.findByCodeContainsAndStatus(code, status.get(),pageable));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data status successfully",
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
    public ResponseEntity<ResponseObject> post(@RequestBody @Validated PromotionDto promotionDto) throws IllegalAccessException, InvocationTargetException, DuplicateEntryException {
        Promotion promotion = new Promotion();
        if(service.findByCode(promotionDto.getCode()).isPresent()){
            throw new DuplicateEntryException("CODE", "Code is exits");
        }
        BeanUtils.copyProperties(promotionDto, promotion);
        Instant startDate = DateHelper.toDate(promotionDto.getStartDate(), "yyyy-MM-dd");
        Instant endDate = DateHelper.toDate(promotionDto.getEndDate(), "yyyy-MM-dd");
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        if (endDate.isBefore(startDate)) {
            promotion.setStatus(false);
        } else {
            promotion.setStatus(true);
        }
        service.save(promotion);
        promotion = service.findByCode(promotionDto.getCode()).get();
        return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Created promotion successfully", promotion));
    
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
        Instant startDate = DateHelper.toDate(promotionDto.getStartDate(), "yyyy-MM-dd");
        Instant endDate = DateHelper.toDate(promotionDto.getEndDate(), "yyyy-MM-dd");
        promotion.setEndDate(endDate);
        promotion.setStartDate(startDate);
        if (endDate.isBefore(Instant.now())) {
            promotion.setStatus(false);
        } else {
            promotion.setStatus(true);
        }
        service.save(promotion);
        promotion = service.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update Promotion Success", promotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Promotion", null));
        }
        Promotion promotion = service.findById(id).get();
        promotion.setDeletedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Delete Promotion Success", service.save(promotion)));
    }
}
