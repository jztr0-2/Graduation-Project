package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Image;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.serializer.ProductSerializer;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.ImageService;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice("public/product-variant")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/products")
public class ProductVariantController {
    @Autowired
    ProductService service;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ImageService imageService;

//    @GetMapping
//    public ResponseEntity<ResponseObject> getTopNewProduct() {
//        Pageable pageable = CustomPageable.getPage("updatedAt", Constant.SORT_DESC);
//        Page<Product> products = service.findAll(pageable);
////        List<ProductVariant> productVariantList = productVariantService.toProductVariant(products.getContent());
//        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
//                "Get list new product successfully", new PageableSerializer(products)));
//    }

    @GetMapping
    public ResponseEntity<ResponseObject> findByName(@RequestParam(defaultValue = "") String name) {
        Pageable pageable = CustomPageable.getPage("updatedAt", Constant.SORT_DESC);
        Page<Product> products = service.findByNameLike(name, pageable);
//        List<ProductVariant> productVariantList = productVariantService.toProductVariant(products.getContent());
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                "Get list new product successfully", new PageableSerializer(products)));
    }

    @GetMapping("/{id}/top_sale")
    public ResponseEntity<ResponseObject> getTopSaleByCategory(@PathVariable String id,
                                                               @RequestParam(defaultValue = "1") Integer page,
                                                               @RequestParam(defaultValue = "10") Integer perPage) {

        Pageable pageable = CustomPageable.getPage(page, perPage);
        Page<Product> products = service.findTopSaleByCategory(Long.valueOf(id), pageable);
        return ResponseEntity.ok(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                "Get list new product successfully", new PageableSerializer(products)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> p = service.findById(id);
        Product product = p.get();
        List<Image> imageList = imageService.findByProductId(id);
        ProductSerializer productSerializer = new ProductSerializer();
        BeanUtils.copyProperties(product, productSerializer);
        if (imageList != null) {
            List<String> imgList = imageList.stream().map(item -> Constant.BASE_API_URL + "public/images/" + item.getTitle()).collect(Collectors.toList());
            productSerializer.setImageList(imgList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Get product successfully", productSerializer)
        );
    }

    @GetMapping("top-category")
    public ResponseEntity<ResponseObject> getTopSaleOfCategory(){
        Category category = categoryService.findTopSaleCategory();
        Page<Product> products = service.findTopSaleByCategory(category.getId(), CustomPageable.getPage(1,10));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK",
                        new PageableSerializer(products).getContent()));
    }

    @GetMapping("top-sale")
    public ResponseEntity<ResponseObject> getTopSaleProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK",
                        service.findTopSale()));
    }
}
