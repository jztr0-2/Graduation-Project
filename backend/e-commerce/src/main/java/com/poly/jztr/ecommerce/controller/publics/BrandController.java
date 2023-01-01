package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("public/brand")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/brand")
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

}
