package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.ProductVariantDto;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController("admin/product-variants")
@RequestMapping("api/v1/admin/variants")
public class ProductVariantsController {
    @Autowired
    ProductVariantService service;


    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody ProductVariantDto dto, @PathVariable Long id){
        ProductVariant variant = service.findById(id);
        variant.setQuantity(dto.getQuantity());
        variant.setDescription(dto.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK",
                        service.save(variant)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ProductVariant variant = service.findById(id);
        variant.setDeletedAt(Instant.now());
        service.save(variant);
        return ResponseEntity.ok().build();
    }
}
