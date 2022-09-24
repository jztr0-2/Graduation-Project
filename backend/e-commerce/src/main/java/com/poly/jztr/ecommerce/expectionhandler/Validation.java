package com.poly.jztr.ecommerce.expectionhandler;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class Validation extends MethodArgumentNotValidException {

    public Validation(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
        handleExeption(parameter, bindingResult);
    }

    private ResponseEntity<ResponseObject> handleExeption(MethodParameter parameter, BindingResult bindingResult) {
        System.out.println("ABCCC");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,"",bindingResult));
    }


}
