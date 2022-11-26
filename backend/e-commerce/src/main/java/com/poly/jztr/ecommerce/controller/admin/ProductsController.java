package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestControllerAdvice("admin/product")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/products")
public class ProductsController {

    @Autowired
    ProductService service;

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody @Validated ProductDto dto) throws DuplicateEntryException {
        Optional<Product> optionalProduct = service.findByName(dto.getName());
        if(optionalProduct.isPresent()) throw new DuplicateEntryException("Name", "Product name is exists");

        if (categoryService.findById(dto.getCategoryId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
                            "Category not found", null));
        }
        Product p = service.toProduct(dto);
        service.save(p);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Create product successfully", service.save(p)));

    }
}
