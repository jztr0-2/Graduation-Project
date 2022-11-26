package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("public/product-variant")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/products")
public class ProductVariantController {
    @Autowired
    ProductService service;

    @Autowired
    ProductVariantService productVariantService;

    @GetMapping("index")
    public ResponseEntity<ResponseObject>getTopNewProduct(){
        Pageable pageable = CustomPageable.getPage("updatedAt", Constant.SORT_DESC);
        Page<Product> products = service.findAll(pageable);
        List<ProductVariant> productVariantList = productVariantService.toProductVariant(products.getContent());
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                "Get list new product successfully", productVariantList));
    }

    @GetMapping("/{id}/top_sale")
    public ResponseEntity<ResponseObject> getTopSaleByCategory(@PathVariable String id,
                                                               @RequestParam(defaultValue = "1") Integer page,
                                                               @RequestParam(defaultValue = "10") Integer perPage){
        Pageable pageable = CustomPageable.getPage(page,perPage);
        Page<Product> productPage = service.findTopSaleByCategory(Long.valueOf(id), pageable);
        List<ProductVariant> productVariantList = productVariantService.toProductVariant(productPage.getContent());
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                "Get list new product successfully", productVariantList));
    }
}
