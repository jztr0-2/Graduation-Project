package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.AddressDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.Address;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController("public/address")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/address")
public class AddressController {

    @Autowired
    AddressService service;

//    @GetMapping("")
//    public ResponseEntity<ResponseObject> getAll() {
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Get Address success", service.findAll()));
//    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getByPhone(@RequestParam(defaultValue = "") String phone) {
        List<Address> listAdr = service.finByPhoneContains(phone);
        if(listAdr.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found address by phone ", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Found address ", service.finByPhoneContains(phone)));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> post(@RequestBody @Validated AddressDto addressDto)   {
        if(addressDto.getId() != null) {
            Optional<Address> id = service.findById(addressDto.getId());
            if(id.isPresent()) {
                Address newAdre = new Address();
                BeanUtils.copyProperties(addressDto, newAdre);
                newAdre.setId(addressDto.getId());
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update Address Success", service.save(newAdre)));
            }
        }
        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Insert address success", service.save(address)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> put(@RequestBody @Validated AddressDto addressDto, @PathVariable("id") Long id)   {
        Optional<Address> addressDb = service.findById(id);
        if(addressDb.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Address not found", null));
        }
        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Update address success", service.save(address)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id){
        Optional<Address> optAdd = service.findById(id);
        if(optAdd.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_NOTFOUND,"Not Found Address", null));
        }
        Address add = optAdd.get();
        service.deleteById(add.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"Delete address success", null));
    }
}
