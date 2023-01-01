package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.BrandDto;
import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.service.BrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(@RequestParam(value = "name",
            defaultValue = "") String name) {
        List<Brand> listBrand = service.findByName(name);

        if(listBrand.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found brand by name ", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Found address ", service.findByName(name)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Brand> optBrand = service.findById(id);
        if (optBrand.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND, "Not found brand", null));
        } else  {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND, "Find  brand", optBrand.get()));
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
}
