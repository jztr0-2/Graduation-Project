package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.ImageService;
import com.poly.jztr.ecommerce.service.OrderItemService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.apache.commons.lang3.math.NumberUtils;
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

@RestControllerAdvice("public/products")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/products")
public class ProductsController {
    @Autowired
    ProductService service;
    @Autowired
    OrderItemService orderItemService;
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
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get product successfully", service.findById(id).get())
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

    @GetMapping("related/{id}")
    public ResponseEntity<ResponseObject> getRelatedProducts(@PathVariable Long id){
        List<Product> products = service.findRelatedProduct(id);
        Map<String, Object> map = new HashMap<>();
        map.put("content", products);
        map.put("count", products.size());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK", map));
    }
    @GetMapping("totals")
    public ResponseEntity<ResponseObject> countProducts() {
        HashMap<String, Integer> responseData = new HashMap<>();
        responseData.put("productTotal", service.countProducts());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK",responseData)
        );
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseObject> searchProducts(
            @RequestParam("q") Optional<String> searchName,
            @RequestParam("type") Optional<String> type,
            @RequestParam("limit") Optional<Integer> limit,
            @RequestParam("currentPage") Optional<Integer> currentPage
            ) {
        // query field is required
        if (searchName.get().equals("")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject("422", "The q field is required", null)
            );
        }
        // type is less response 5 products, type is more response 8 products
        Pageable pageable;
        String queryType = type.get();
        if (queryType.startsWith("less")) {
            pageable = CustomPageable.getPage(currentPage.orElse(1), limit.orElse(5));
        } else {
            pageable = CustomPageable.getPage(currentPage.orElse(1), limit.orElse(8));
        }
        Page page = service.searchProducts("%" + searchName.get() + "%", pageable);
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("totalItems", page.getTotalElements());
        responseData.put("totalPages", page.getTotalPages());
        responseData.put("perPage", page.getPageable().getPageSize());
        responseData.put("nextPage", page.hasNext());
        responseData.put("prevPage", page.hasPrevious());
        responseData.put("products", page.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK", responseData)
        );
    }
    @GetMapping("/sold/{id}")
    public ResponseEntity<ResponseObject> totalSoldProductsByProductId(
            @PathVariable Long id
    ) {
        Integer total = orderItemService.totalSoldProductsByProductId(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK", Optional.ofNullable(total).orElse(0))
        );
    }
    @GetMapping("/random")
    public ResponseEntity<ResponseObject> randomProducts(
        @RequestParam("id") Long id,
        @RequestParam("limit") Optional<Integer> limit
    ) {
        List<Product> products = service.randomProducts(id, limit.orElse(10));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK", products)
        );
    }
}
