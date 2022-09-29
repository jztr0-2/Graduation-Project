package com.poly.jztr.ecommerce.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleValidateExection(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(err ->{
           errors.put(err.getField(),err.getDefaultMessage());
        });
        return ResponseError.build(HttpStatus.UNPROCESSABLE_ENTITY,"Unprocessable entity", errors);
    }
}
