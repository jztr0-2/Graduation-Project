package com.poly.jztr.ecommerce.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
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

    @ExceptionHandler(DuplicateEntryException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleDuplicateEntry(DuplicateEntryException exception){
        Map<String, String> errors = new HashMap<String, String>();
        errors.put(exception.getField(),exception.getMessage());
        return ResponseError.build(HttpStatus.UNPROCESSABLE_ENTITY,"Unprocessable entity", errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleTypeMismatch(){
        Map<String, String> errors = new HashMap<String, String>();
        return ResponseError.build(HttpStatus.UNPROCESSABLE_ENTITY,"Data not match", errors);
    }

}
