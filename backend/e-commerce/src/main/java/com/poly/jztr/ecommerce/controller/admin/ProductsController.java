package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice("admin/product")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/products")
public class ProductsController {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody @Validated ProductDto dto){
        Product p = service.toProduct(dto);
        service.save(p);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Create product successfully", service.save(p)));

    }
}
