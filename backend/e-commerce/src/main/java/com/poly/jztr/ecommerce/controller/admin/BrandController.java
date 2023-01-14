package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.BrandDto;
import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.BrandService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice("admin/brands")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/brands")
public class BrandController {

    @Autowired
    BrandService service;

    @Autowired
    ProductService servicePro;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findByStatus(@RequestParam(defaultValue = "") String name,
                                                       @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer perPage){
        PageableSerializer data = null;
        Pageable pageable = CustomPageable.getPage(page, perPage);
        if("".equalsIgnoreCase(name)) data = new PageableSerializer(service.getAll(pageable));
        else data = new PageableSerializer(service.findByName(name,pageable));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get data status successfully",
                        data));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Brand> optBrand = service.findById(id);
        if (optBrand.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND, "Not found brand", null));
        } else  {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Find  brand", optBrand.get()));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody @Validated BrandDto dto) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(dto, brand);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Create brand success", service.save(brand)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody @Validated BrandDto dto,
                                                 @PathVariable("id") Long id) {
        Optional<Brand> optBrand = service.findById(id);
        if (optBrand.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND, "Not found brand", null));
        }
        Brand brand = new Brand();
        BeanUtils.copyProperties(dto, brand);
        brand.setId(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Update brand success", service.save(brand)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id){
        Optional<Brand> brand = service.findById(id);
        if(brand.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Brand", null));
        }

        List<Product> list = servicePro.findByBrandId(brand.get());

        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_FAILED,
                            "Can not delete brand because it being used in product", service.findById(id).get()));
        } else {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Delete brand success", null));
        }
    }
}
