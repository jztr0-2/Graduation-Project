package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.BrandService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("public/brand")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/brands")
public class BrandController {

    @Autowired
    BrandService service;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByStatus(@RequestParam(defaultValue = "") String name,
                                                       @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer perPage) {
        PageableSerializer data = null;
        Pageable pageable = CustomPageable.getPage(page, perPage);
        if ("".equalsIgnoreCase(name)) data = new PageableSerializer(service.getAll(pageable));
        else data = new PageableSerializer(service.findByName(name, pageable));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get data status successfully",
                        data));
    }

    @GetMapping("/index")
    public ResponseEntity<ResponseObject> findAll() {
        List<Long> ids = service.findBrandExistProduct();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get data status successfully",
                        service.findByIdIn(ids)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer perPage) {

        Page<Product> products = productService.findByBrandAndQuantityIsGreaterThan(new Brand(id),0,
                CustomPageable.getPage(page,perPage));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get category successfully",
                        new PageableSerializer(products)));
    }

}
