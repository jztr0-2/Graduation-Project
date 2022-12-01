package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> p = service.findById(id);
        if(p.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get product successfully", p.get() )
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseObject> getPageProducts(
        @PathVariable Long id,
        @RequestParam("page") Optional<Integer> page,
        @RequestParam("limit") Optional<Integer> limit) {
            if (id == null) {
                return null;
            }
            // define pagination
            Pageable pageable = CustomPageable.getPage(page.orElse(1), limit.orElse(10));
            Page<Product> pageProduct = service.getProductsByCategoryId(id, pageable);
            Map<String, Object> customResponse = new HashMap<>();
            customResponse.put("products", productVariantService.toProductVariant(pageProduct.getContent()));
            customResponse.put("total_pages", pageProduct.getTotalPages());
            customResponse.put("total_items", pageProduct.getTotalElements());
            customResponse.put("next_page", pageProduct.hasNext());
            customResponse.put("prev_page", pageProduct.hasPrevious());
            // response data
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get products successfully", customResponse)
            );

    }
}
