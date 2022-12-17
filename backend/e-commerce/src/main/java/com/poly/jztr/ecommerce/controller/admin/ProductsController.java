package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
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
        p = service.save(p);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Create product successfully", service.save(p)));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> index(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam (defaultValue = "10") Integer perPage,
                                                @RequestParam(defaultValue = "") String name,
                                                @RequestParam(defaultValue = "-1") Integer status){
        Pageable pageable = CustomPageable.getPage(page,perPage,"updatedAt", Constant.SORT_DESC);
        PageableSerializer data = null;
        if(status == -1) data = new PageableSerializer(service.findByNameLike(name,pageable));
        else data = new PageableSerializer(service.findByNameContainsAndStatus(name,status,pageable));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                            "Get products successfully", data));
    }

    @GetMapping("{/id}")
    public ResponseEntity<ResponseObject> getOne(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        Constant.RESPONSE_STATUS_SUCCESS, "Get product success",
                        service.findById(id).get())
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody @Validated ProductDto dto ) throws DuplicateEntryException {
        if (categoryService.findById(dto.getCategoryId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,
                            "Category not found", null));
        }

        Product p = service.toProduct(dto);
        p.setId(id);
        try {
            service.save(p);
        }catch (Exception e){
            throw new DuplicateEntryException("Name","Product name is exists");
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Update successfully", null));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        Product p = service.findById(id).get();
        p.setDeletedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Delete successfully", service.save(p)));
    }
}
