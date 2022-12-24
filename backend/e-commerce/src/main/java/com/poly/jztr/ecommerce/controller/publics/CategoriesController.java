package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.ImageService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController("public/categories")
@RequestMapping("api/v1/public/categories")
public class CategoriesController {
    @Autowired
    CategoryService service;
    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAll(){
        List<Category> categories = service.findAll();
        // filter categories is exists products
        List<Category> filterCategories = categories.stream().filter(category -> !category.getProducts().isEmpty()).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get category successfully",
                        filterCategories));
    }
    @GetMapping("/views/page")
    public ResponseEntity<ResponseObject> getPageCategory(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("limit") Optional<Integer> limit
    ) {
        Pageable pageable = CustomPageable.getPage(page.orElse(1), limit.orElse(10));
        Page<Category> categories = service.findPageCategory(pageable);
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
                            "Categories is empty data",
                            new ArrayList<>()));
        }
        // prepare response
        Map<String, Object> customResponse = new HashMap<>();
        customResponse.put("categories", categories.getContent());
        customResponse.put("totalPages", categories.getTotalPages());
        customResponse.put("totalItems", categories.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get categories successfully",
                        customResponse));
    }
    @GetMapping("/views")
    public ResponseEntity<ResponseObject> getAllIncludeProducts(
            @RequestParam("page") Optional<Integer> page
    ) {
        List<Category> categories = service.findAll();
        // filter categories is exists products
        List<Category> filterCategories = categories.stream().filter(category -> !category.getProducts().isEmpty()).collect(Collectors.toList());

        // check if categories is empty data
        if (filterCategories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
                            "Categories is empty data",
                            new ArrayList<>()));
        }

        // Get products from category id;
        Category category = filterCategories.get(Math.min(page.get() - 1, filterCategories.size() - 1));
        List<Product> products = productService.getProductsByCategoryId(category.getId(),
                CustomPageable.getPage(1, 10)).getContent();

        // check if products is empty
        if (products.isEmpty())  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
                            "Category not empty products",
                            new ArrayList<>()));
        }

        // prepare response
        Map<String, Object> customResponse = new HashMap<>();
        customResponse.put("category", category);
        customResponse.put("products", products);
        customResponse.put("totalPages", filterCategories.size());
        customResponse.put("totalItems", filterCategories.size());

        // prepare response
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get products by category id {" + category.getId() + "} successfully",
                        customResponse));
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseObject> getById(
//            @PathVariable Long id,
//            @RequestParam("page") Optional<Integer> page,
//            @RequestParam("limit") Optional<Integer> limit
//    ) {
//        Optional<Category> categoryOptional = service.findById(id);
//        // check category is empty
//        if (categoryOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
//                            id + " is not exists",
//                            new ArrayList<>()));
//        }
//        // Define pagination
//        Pageable pageable = CustomPageable.getPage(page.orElse(1), limit.orElse(10));
//        // Get products from category id
//        Page<Product> productPage = productService.getProductsByCategoryId(id, pageable);
//        if (productPage.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
//                            "Category not empty products",
//                            new ArrayList<>()));
//        }
//        // convert products to products serializer
//        CategoriesControllerService controllerService = new CategoriesControllerService();
//
//        Map<String, Object> customResponse = new HashMap<>();
//        customResponse.put("products", products);
//        customResponse.put("totalPages", productPage.getTotalPages());
//        customResponse.put("totalItems", productPage.getTotalElements());
//        customResponse.put("nextPage", productPage.hasNext());
//        customResponse.put("prevPage", productPage.hasPrevious());
//
//        // prepare response
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
//                        "Get products by category id {" + id + "} successfully",
//                        customResponse));
//    }
    @GetMapping("/{id}/top_sale")
    public ResponseEntity<ResponseObject> getTopSaleByCategory(@PathVariable String id){
        return  null;
    }



//    class CategoriesControllerService {
//        public List<ProductSerializer> toProductSerializer(List<Product> products) {
//            List<ProductSerializer> productSerializers = new ArrayList<>();
//            for (Product product : products) {
//                List<Image> imageList = imageService.findByProductId(product.getId());
//                ProductSerializer productSerializer = new ProductSerializer();
//                if (imageList != null) {
//                    BeanUtils.copyProperties(product, productSerializer);
//                    List<String> imgList = imageList.stream().map(item -> Constant.BASE_API_URL + "public/images/" + item.getTitle()).collect(Collectors.toList());
//                    productSerializer.setImageList(imgList);
//                }
//                productSerializers.add(productSerializer);
//            }
//            return productSerializers;
//        }
//    }
}

